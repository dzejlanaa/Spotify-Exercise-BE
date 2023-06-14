package ba.com.app.sdr.test.suites;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.assertj.core.api.Assertions;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import ba.com.app.commons.exception.ApiException;
import ba.com.app.commons.message.request.EntityRequest;
import ba.com.app.commons.message.request.FilterRequest;
import ba.com.app.commons.message.response.PayloadResponse;
import ba.com.app.commons.model.PagedData;
import ba.com.app.commons.model.QueryConditionPage;
import ba.com.app.commons.model.enums.Status;
import ba.com.app.commons.validation.RequestValidator;
import ba.com.app.sdr.api.SpotifyIntegrationService;
import ba.com.app.sdr.api.model.spotifyintegration.SpotifyIntegrationCreateRequest;
import ba.com.app.sdr.api.model.spotifyintegration.SpotifyIntegrationResponse;
import ba.com.app.sdr.api.model.spotifyintegration.SpotifyIntegrationUpdateRequest;
import ba.com.app.sdr.core.impl.SpotifyIntegrationServiceImpl;
import ba.com.app.sdr.core.mapper.SpotifyIntegrationMapper;
import ba.com.app.sdr.core.validation.SpotifyIntegrationRequestValidation;
import ba.com.app.sdr.dao.SpotifyIntegrationDAO;
import ba.com.app.sdr.dao.model.SpotifyIntegrationEntity;
import ba.com.app.sdr.test.configuration.BasicTestConfiguration;

public class SpotifyIntegrationServiceTest extends BasicTestConfiguration {
    @Autowired
    private SpotifyIntegrationMapper spotifyIntergationMapper;
    private SpotifyIntegrationDAO spotifyIntergationDAO;
    private RequestValidator requestValidator;
    private SpotifyIntegrationRequestValidation spotifyIntergationRequestValidation;
    private SpotifyIntegrationService spotifyIntergationService;

    @BeforeMethod
    public void beforeMethod() throws ApiException {
        this.requestValidator = Mockito.mock(RequestValidator.class);
        this.spotifyIntergationDAO = Mockito.mock(SpotifyIntegrationDAO.class);
        this.spotifyIntergationRequestValidation = Mockito.mock(SpotifyIntegrationRequestValidation.class);
        this.spotifyIntergationService = new SpotifyIntegrationServiceImpl(spotifyIntergationDAO, spotifyIntergationMapper,
                spotifyIntergationRequestValidation, null, null, null);
    }

    @Test(enabled = true)
    public void testFindSpotifyIntegration() {
        try {
            List<SpotifyIntegrationEntity> entities = new ArrayList<>();
            SpotifyIntegrationEntity firstSpotifyIntegrationEntity = new SpotifyIntegrationEntity();

            firstSpotifyIntegrationEntity.setRequest("Test Request 1");
            firstSpotifyIntegrationEntity.setName("Test Name 1");
            firstSpotifyIntegrationEntity.setResponse("Test Response 1");

            SpotifyIntegrationEntity secondSpotifyIntegrationEntity = new SpotifyIntegrationEntity();
            secondSpotifyIntegrationEntity.setRequest("Test Request 2");
            secondSpotifyIntegrationEntity.setName("Test Name 2");
            secondSpotifyIntegrationEntity.setResponse("Test Response 2");

            SpotifyIntegrationEntity thirdSpotifyIntegrationEntity = new SpotifyIntegrationEntity();
            thirdSpotifyIntegrationEntity.setRequest("Test Request 3");
            thirdSpotifyIntegrationEntity.setName("Test Name 3");
            thirdSpotifyIntegrationEntity.setResponse("Test Response 3");

            entities.add(firstSpotifyIntegrationEntity);
            entities.add(secondSpotifyIntegrationEntity);
            entities.add(thirdSpotifyIntegrationEntity);

            PagedData<SpotifyIntegrationEntity> pagedEntites = new PagedData<>();
            pagedEntites.setRecords(entities);
            List<SpotifyIntegrationResponse> response = new ArrayList<>();

            SpotifyIntegrationResponse firstResponse = new SpotifyIntegrationResponse();
            firstResponse.setRequest("Test Request 1");
            firstResponse.setName("Test Name 1");
            firstResponse.setResponse("Test Response 1");

            SpotifyIntegrationResponse secondResponse = new SpotifyIntegrationResponse();
            secondResponse.setRequest("Test Request 2");
            secondResponse.setName("Test Name 2");
            secondResponse.setResponse("Test Response 2");

            SpotifyIntegrationResponse thirdResponse = new SpotifyIntegrationResponse();
            thirdResponse.setRequest("Test Request 3");
            thirdResponse.setName("Test Name 3");
            thirdResponse.setResponse("Test Response 3");

            response.add(firstResponse);
            response.add(secondResponse);
            response.add(thirdResponse);

            PagedData<SpotifyIntegrationResponse> pagedResponse = new PagedData<>();
            pagedResponse.setRecords(response);
            Map<String, Object> filterCriteria = new HashMap<String, Object>();
            QueryConditionPage queryConditionPage = new QueryConditionPage();
            FilterRequest filterRequest = new FilterRequest(filterCriteria, queryConditionPage);
            Mockito.when(requestValidator.validate(filterRequest)).thenReturn(null);
            Mockito.when(spotifyIntergationDAO.findAll(filterRequest.getFilter())).thenReturn(pagedEntites);
            List<SpotifyIntegrationResponse> spotifyIntegrationFindResponse = spotifyIntergationService.find(filterRequest).getPayload();
            Assertions.assertThat(spotifyIntegrationFindResponse).as("Check all elements")
                    .overridingErrorMessage("All elements should be equal.").hasSameElementsAs(response);
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test(enabled = true)
    public void testCreateSpotifyIntegration() {
        try {
            EntityRequest<SpotifyIntegrationCreateRequest> req = new EntityRequest<>();
            var newSpotifyIntegrationRequest = new SpotifyIntegrationCreateRequest();
            newSpotifyIntegrationRequest.setRequest("Test Information");
            newSpotifyIntegrationRequest.setName("Test Type 1");
            newSpotifyIntegrationRequest.setResponse("Response 1");
            req.setEntity(newSpotifyIntegrationRequest);
            var newSpotifyIntegrationEnt = new SpotifyIntegrationEntity();
            newSpotifyIntegrationEnt.setRequest("Test Information");
            newSpotifyIntegrationEnt.setName("Test Type 1");
            newSpotifyIntegrationEnt.setResponse("Response 1");
            var newSpotifyIntegration = new SpotifyIntegrationResponse();
            newSpotifyIntegration.setRequest("Test Information");
            newSpotifyIntegration.setName("Test Type 1");
            newSpotifyIntegration.setResponse("Response 1");
            newSpotifyIntegration.setStatus(Status.ACTIVE.getValue());
            Mockito.when(spotifyIntergationDAO.persist(newSpotifyIntegrationEnt)).thenReturn(null);
            PayloadResponse<SpotifyIntegrationResponse> SpotifyIntegrationFindResponse = spotifyIntergationService.create(req);
            Assertions.assertThat(SpotifyIntegrationFindResponse.getPayload()).as("Check all fields").usingRecursiveComparison()
                    .ignoringFields("created", "createdBy", "modified", "modifiedBy").isEqualTo(newSpotifyIntegration);
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test(enabled = true)
    public void testUpdateSpotifyIntegration() {
        try {
            EntityRequest<SpotifyIntegrationUpdateRequest> request = new EntityRequest<>();
            SpotifyIntegrationEntity spotifyIntegrationEntity = new SpotifyIntegrationEntity();
            spotifyIntegrationEntity.setRequest("Old Test Information 2");
            spotifyIntegrationEntity.setName("Old Test Name 1");
            spotifyIntegrationEntity.setResponse("Old Test Information");
            SpotifyIntegrationResponse spotifyIntegrationResponse = new SpotifyIntegrationResponse();
            spotifyIntegrationResponse.setRequest("Update Test Information 2");
            spotifyIntegrationResponse.setName("Update Test Name 1");
            spotifyIntegrationResponse.setResponse("Update Test Information");
            SpotifyIntegrationUpdateRequest updatespotifyIntegrationRequest = new SpotifyIntegrationUpdateRequest();
            updatespotifyIntegrationRequest.setRequest("Update Test Information 2");
            updatespotifyIntegrationRequest.setName("Update Test Name 1");
            updatespotifyIntegrationRequest.setResponse("Update Test Information");
            request.setEntity(updatespotifyIntegrationRequest);
            Mockito.when(spotifyIntergationRequestValidation.validateUpdateSpotifyIntegrationRequest(request)).thenReturn(null);
            Mockito.when(spotifyIntergationDAO.findByPK(request.getEntity().getId())).thenReturn(spotifyIntegrationEntity);
            Mockito.doNothing().when(spotifyIntergationDAO).merge(spotifyIntegrationEntity);
            var spotifyIntegrationUpdateResponse = spotifyIntergationService.update(request);
            Assertions.assertThat(spotifyIntegrationUpdateResponse.getPayload()).as("Check all fields")
                    .overridingErrorMessage("All fields should be equal.").usingRecursiveComparison()
                    .ignoringFields("created", "createdBy", "modified", "modifiedBy").isEqualTo(spotifyIntegrationResponse);
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test(enabled = true)
    public void testDeleteSpotifyIntegration() {
        try {
            var req = new EntityRequest<Long>();
            req.setEntity(1L);
            Mockito.when(requestValidator.validate(req)).thenReturn(null);
            Mockito.doNothing().when(spotifyIntergationDAO).removeByPK(req.getEntity());
            var spotifyIntegrationFindResponse = spotifyIntergationService.delete(req);
            Assertions.assertThat(spotifyIntegrationFindResponse.getPayload()).isEqualTo("Integration removed successfully!");
        } catch (Exception e) {
            Assert.fail();
        }
    }
}
