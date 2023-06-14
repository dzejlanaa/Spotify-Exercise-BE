package ba.com.app.sdr.core.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ba.com.app.commons.message.request.EmptyRequest;
import ba.com.app.commons.message.request.EntityRequest;
import ba.com.app.commons.message.request.FilterRequest;
import ba.com.app.commons.message.response.PagedPayloadResponse;
import ba.com.app.commons.message.response.PayloadResponse;
import ba.com.app.commons.model.PagedData;
import ba.com.app.commons.model.enums.Status;
import ba.com.app.commons.model.response.ResponseCode;
import ba.com.app.sdr.api.SpotifyIntegrationService;
import ba.com.app.sdr.api.model.spotifyintegration.SpotifyIntegrationCreateRequest;
import ba.com.app.sdr.api.model.spotifyintegration.SpotifyIntegrationResponse;
import ba.com.app.sdr.api.model.spotifyintegration.SpotifyIntegrationUpdateRequest;
import ba.com.app.sdr.core.mapper.SpotifyIntegrationMapper;
import ba.com.app.sdr.core.validation.SpotifyIntegrationRequestValidation;
import ba.com.app.sdr.dao.AlbumDAO;
import ba.com.app.sdr.dao.ArtistDAO;
import ba.com.app.sdr.dao.SongDAO;
import ba.com.app.sdr.dao.SpotifyIntegrationDAO;
import ba.com.app.sdr.dao.model.SpotifyIntegrationEntity;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SpotifyIntegrationServiceImpl implements SpotifyIntegrationService {

    SpotifyIntegrationDAO spotifyIntegrationDAO;
    SpotifyIntegrationMapper spotifyIntegrationMapper;
    SpotifyIntegrationRequestValidation spotifyIntegrationRequestValidation;
    SongDAO songDAO;
    ArtistDAO artistDAO;
    AlbumDAO albumDAO;

    @Override
    public PagedPayloadResponse<SpotifyIntegrationResponse> find(final FilterRequest request) {
        PagedData<SpotifyIntegrationEntity> spotifyIntegrationEntities = spotifyIntegrationDAO.findAll(request.getFilter());
        return new PagedPayloadResponse<>(request, ResponseCode.OK, spotifyIntegrationEntities, spotifyIntegrationMapper::entitiesToDtos);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<SpotifyIntegrationResponse> create(final EntityRequest<SpotifyIntegrationCreateRequest> request) {
        spotifyIntegrationRequestValidation.validateCreateSpotifyIntegrationRequest(request);

        var spotifyIntegrationEntity = spotifyIntegrationMapper.dtoToEntity(request.getEntity());
        spotifyIntegrationEntity.setCreated(LocalDateTime.now());
        spotifyIntegrationEntity.setCreatedBy(request.getUserId());
        spotifyIntegrationEntity.setStatus(Status.ACTIVE.value());

        spotifyIntegrationDAO.persist(spotifyIntegrationEntity);
        return new PayloadResponse<>(request, ResponseCode.OK, spotifyIntegrationMapper.entityToDto(spotifyIntegrationEntity));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<SpotifyIntegrationResponse> update(final EntityRequest<SpotifyIntegrationUpdateRequest> request) {
        spotifyIntegrationRequestValidation.validateUpdateSpotifyIntegrationRequest(request);

        var spotifyIntegrationEntity = spotifyIntegrationDAO.findByPK(request.getEntity().getId());
        spotifyIntegrationMapper.updateEntity(request.getEntity(), spotifyIntegrationEntity);

        spotifyIntegrationEntity.setModified(LocalDateTime.now());
        spotifyIntegrationEntity.setModifiedBy(request.getUserId());
        spotifyIntegrationDAO.merge(spotifyIntegrationEntity);
        return new PayloadResponse<>(request, ResponseCode.OK, spotifyIntegrationMapper.entityToDto(spotifyIntegrationEntity));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayloadResponse<String> delete(final EntityRequest<Long> request) {
        spotifyIntegrationRequestValidation.validateExistsSpotifyIntegrationRequest(request);

        var spotifyIntegrationEntity = spotifyIntegrationDAO.findByPK(request.getEntity());
        spotifyIntegrationDAO.remove(spotifyIntegrationEntity);
        return new PayloadResponse<>(request, ResponseCode.OK, "Integration removed successfully!");
    }

   

}
