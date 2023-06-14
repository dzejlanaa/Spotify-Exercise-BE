package ba.com.app.sdr.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import ba.com.app.commons.dao.AbstractDAO;
import ba.com.app.sdr.dao.model.SpotifyIntegrationEntity;

@Repository
public class SpotifyIntegrationDAO extends AbstractDAO<SpotifyIntegrationEntity, Long> {


    public String getResponseByObjectIdAndObjectType(Long objectId, String objectType) {
        var hql = "select si.response from SpotifyIntegrationEntity si where si.objectId=:objectId and si.objectType like :objectType";
        return entityManager.createQuery(hql, String.class).setParameter("objectType", objectType).setParameter("objectId", objectId)
                .setMaxResults(1).getSingleResult();
    }

}
