package rest_demo.util;

import rest_demo.dao.LookupRepository;
import rest_demo.entity.LookupEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class Constants {
    private Map<String, String> messages;

    @Autowired
    LookupRepository lookupRepo;

    @PostConstruct
    public void init() {
       messages = new HashMap<>();
       List<LookupEntity> userMessages = lookupRepo.findByType("USER_MESSAGE");
       userMessages.forEach(m -> messages.put(m.getName(), m.getValue()));
    }

    public String getMessage(String msgName) {
       return messages.get(msgName);
    }
}
