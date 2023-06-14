package ba.com.app.sdr.core.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import ba.com.app.sdr.api.model.spotifyintegration.SpotifyIntegrationCreateRequest;
import ba.com.app.sdr.api.model.spotifyintegration.SpotifyIntegrationResponse;
import ba.com.app.sdr.api.model.spotifyintegration.SpotifyIntegrationUpdateRequest;
import ba.com.app.sdr.dao.model.SpotifyIntegrationEntity;

@Mapper(componentModel = "spring")
public interface SpotifyIntegrationMapper {

    SpotifyIntegrationEntity dtoToEntity(SpotifyIntegrationCreateRequest moritsIntegration);

    SpotifyIntegrationResponse entityToDto(SpotifyIntegrationEntity moritsIntegrationEntity);

    void updateEntity(SpotifyIntegrationUpdateRequest moritsIntegration, @MappingTarget SpotifyIntegrationEntity moritsIntegrationEntity);

    List<SpotifyIntegrationResponse> entitiesToDtos(List<SpotifyIntegrationEntity> moritsIntegrationEntity);

}
