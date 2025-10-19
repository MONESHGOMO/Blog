package blog.com.Blog.Application.service.latestUpdates;

import blog.com.Blog.Application.DTO.LatestUpdatesDTO;
import blog.com.Blog.Application.Exceptions.UnauthorizedAccessException;
import blog.com.Blog.Application.model.LatestUpdates;
import blog.com.Blog.Application.repository.LatestUpdatesRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class LatestUpdatesService {



    @Autowired
    private LatestUpdatesRepository latestUpdatesRepository;


    public boolean addLatestUpdate(LatestUpdatesDTO latestUpdatesDTO) {

        LatestUpdates addLatestUpdate = new LatestUpdates();
        addLatestUpdate.setContentOne(latestUpdatesDTO.getContentOne());
        addLatestUpdate.setURL_1(latestUpdatesDTO.getURL_1());
        addLatestUpdate.setLive(true);

        latestUpdatesRepository.save(addLatestUpdate);
        return true ;
    }


    public Map<String, Object> getLatestUpdate() {


        LatestUpdates latestUpdates = latestUpdatesRepository.getTheLatestUpdateFromDB();

        if (latestUpdates == null) {
            return Map.of("status", "error", "message", "No updates found");
        }

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("id", latestUpdates.getId());
        response.put("contentOne", latestUpdates.getContentOne());
        response.put("contentTwo", latestUpdates.getContentTwo());
        response.put("live", latestUpdates.isLive());
        response.put("url_1", latestUpdates.getURL_1());
        response.put("url_2", latestUpdates.getURL_2());

        return response;
    }


    public List<LatestUpdates> getAllLatestUpdate() {
        return  latestUpdatesRepository.findAll();
    }

    public Boolean deleteLatestUpdateByID(Long id) {

        if(latestUpdatesRepository.existsById(id)){
            latestUpdatesRepository.deleteById(id);
            return true;
        }
        else{
            return false;
        }
    }

    @Transactional
    public Boolean updateLiveStatus(Long id, Boolean isLive) {
        Optional<LatestUpdates> updateOpt = latestUpdatesRepository.findById(id);
        if (updateOpt.isPresent()) {
            LatestUpdates update = updateOpt.get();
            update.setLive(isLive);
            latestUpdatesRepository.save(update);
            return true;
        }
        return false;
    }
}
