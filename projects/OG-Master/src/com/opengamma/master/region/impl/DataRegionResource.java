/**
 * Copyright (C) 2009 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.master.region.impl;

import java.net.URI;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Providers;

import com.opengamma.id.ObjectId;
import com.opengamma.id.ObjectIdentifiable;
import com.opengamma.id.UniqueId;
import com.opengamma.id.VersionCorrection;
import com.opengamma.master.region.RegionDocument;
import com.opengamma.master.region.RegionHistoryRequest;
import com.opengamma.master.region.RegionHistoryResult;
import com.opengamma.master.region.RegionMaster;
import com.opengamma.transport.jaxrs.FudgeRest;
import com.opengamma.util.ArgumentChecker;
import com.opengamma.util.rest.AbstractDataResource;

/**
 * RESTful resource for an region.
 */
public class DataRegionResource extends AbstractDataResource {

  /**
   * The regions resource.
   */
  private final DataRegionMasterResource _regionsResource;
  /**
   * The identifier specified in the URI.
   */
  private ObjectId _urlResourceId;

  /**
   * Creates the resource.
   * 
   * @param regionsResource  the parent resource, not null
   * @param regionId  the region unique identifier, not null
   */
  public DataRegionResource(final DataRegionMasterResource regionsResource, final ObjectId regionId) {
    ArgumentChecker.notNull(regionsResource, "regionsResource");
    ArgumentChecker.notNull(regionId, "region");
    _regionsResource = regionsResource;
    _urlResourceId = regionId;
  }

  //-------------------------------------------------------------------------
  /**
   * Gets the regions resource.
   * 
   * @return the regions resource, not null
   */
  public DataRegionMasterResource getRegionsResource() {
    return _regionsResource;
  }

  /**
   * Gets the region identifier from the URL.
   * 
   * @return the unique identifier, not null
   */
  public ObjectId getUrlRegionId() {
    return _urlResourceId;
  }

  //-------------------------------------------------------------------------
  /**
   * Gets the region master.
   * 
   * @return the region master, not null
   */
  public RegionMaster getRegionMaster() {
    return getRegionsResource().getRegionMaster();
  }

  //-------------------------------------------------------------------------
  @GET
  public Response get(@QueryParam("versionAsOf") String versionAsOf, @QueryParam("correctedTo") String correctedTo) {
    VersionCorrection vc = VersionCorrection.parse(versionAsOf, correctedTo);
    RegionDocument result = getRegionMaster().get(getUrlRegionId(), vc);
    return Response.ok(result).build();
  }

  @POST
  @Consumes(FudgeRest.MEDIA)
  public Response update(@Context UriInfo uriInfo, RegionDocument request) {
    if (getUrlRegionId().equals(request.getUniqueId().getObjectId()) == false) {
      throw new IllegalArgumentException("Document objectId does not match URI");
    }
    RegionDocument result = getRegionMaster().update(request);
    URI uri = uriVersion(uriInfo.getBaseUri(), result.getUniqueId());
    return Response.created(uri).entity(result).build();
  }

  @DELETE
  @Consumes(FudgeRest.MEDIA)
  public Response remove() {
    getRegionMaster().remove(getUrlRegionId().atLatestVersion());
    return Response.noContent().build();
  }

  //-------------------------------------------------------------------------
  @GET
  @Path("versions")
  public Response history(@Context Providers providers, @QueryParam("msg") String msgBase64) {
    RegionHistoryRequest request = decodeBase64(RegionHistoryRequest.class, providers, msgBase64);
    if (getUrlRegionId().equals(request.getObjectId()) == false) {
      throw new IllegalArgumentException("Document objectId does not match URI");
    }
    RegionHistoryResult result = getRegionMaster().history(request);
    return Response.ok(result).build();
  }

  @GET
  @Path("versions/{versionId}")
  public Response getVersioned(@PathParam("versionId") String versionId) {
    UniqueId uniqueId = getUrlRegionId().atVersion(versionId);
    RegionDocument result = getRegionMaster().get(uniqueId);
    return Response.ok(result).build();
  }

  @POST
  @Path("versions/{versionId}")
  @Consumes(FudgeRest.MEDIA)
  public Response correct(@Context UriInfo uriInfo, @PathParam("versionId") String versionId, RegionDocument request) {
    UniqueId uniqueId = getUrlRegionId().atVersion(versionId);
    if (uniqueId.equals(request.getUniqueId()) == false) {
      throw new IllegalArgumentException("Document uniqueId does not match URI");
    }
    RegionDocument result = getRegionMaster().correct(request);
    URI uri = uriVersion(uriInfo.getBaseUri(), result.getUniqueId());
    return Response.created(uri).entity(result).build();
  }

  //-------------------------------------------------------------------------
  /**
   * Builds a URI for the resource.
   * 
   * @param baseUri  the base URI, not null
   * @param objectId  the object identifier, not null
   * @param vc  the version-correction locator, null for latest
   * @return the URI, not null
   */
  public static URI uri(URI baseUri, ObjectIdentifiable objectId, VersionCorrection vc) {
    UriBuilder bld = UriBuilder.fromUri(baseUri).path("/regions/{regionId}");
    if (vc != null) {
      bld.queryParam("versionAsOf", vc.getVersionAsOfString());
      bld.queryParam("correctedTo", vc.getCorrectedToString());
    }
    return bld.build(objectId.getObjectId());
  }

  /**
   * Builds a URI for the versions of the resource.
   * 
   * @param baseUri  the base URI, not null
   * @param objectId  the object identifier, not null
   * @param searchMsg  the search message, may be null
   * @return the URI, not null
   */
  public static URI uriVersions(URI baseUri, ObjectIdentifiable objectId, String searchMsg) {
    UriBuilder bld = UriBuilder.fromUri(baseUri).path("/regions/{regionId}/versions");
    if (searchMsg != null) {
      bld.queryParam("msg", searchMsg);
    }
    return bld.build(objectId.getObjectId());
  }

  /**
   * Builds a URI for a specific version of the resource.
   * 
   * @param baseUri  the base URI, not null
   * @param uniqueId  the unique identifier, not null
   * @return the URI, not null
   */
  public static URI uriVersion(URI baseUri, UniqueId uniqueId) {
    if (uniqueId.isLatest()) {
      return uri(baseUri, uniqueId, null);
    }
    UriBuilder bld = UriBuilder.fromUri(baseUri).path("/regions/{regionId}/versions/{versionId}");
    return bld.build(uniqueId.toLatest(), uniqueId.getVersion());
  }

}
