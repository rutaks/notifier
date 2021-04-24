package rw.rutakayile.k8sproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import rw.rutakayile.k8sproject.repository.ClientRepository;
import rw.rutakayile.k8sproject.service.AccessCredentialService;
import rw.rutakayile.k8sproject.service.impl.NotificationServiceImpl;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/notifications/")
public class NotificationController {

    @Autowired
    private NotificationServiceImpl sseService;
    @Autowired
    private AccessCredentialService accessCredentialService;
    @Autowired
    private ClientRepository clientRepository;

    @GetMapping("listen/{apiKey}")
    public String testAPI(@PathVariable String apiKey) {
        // TODO: MAKE PROCESS TO COUNT NO. REQUEST MADE BY AN APIKEY AND BLOCK REQUEST IF REQUEST HAVE EXCEEDED
        // ADD COUNT OF REQUEST TO ACCESS CREDENTIALS OBJECT. ADD LIMIT PER MONTH FOR API KEY
        // ADD GUAVA & NGINX RATE LIMITING.
//        System.out.println("apiKey");
//        System.out.println(apiKey);
//        AccessCredential accessCredential = accessCredentialService.findByApiKey(apiKey);
//        System.out.println(accessCredential);
//        System.out.println("accessCredential");
//        System.out.println(clientRepository.findByNames("Awesomity Ltd"));
//        long difference_In_Time = accessCredential.getLastUpdatedOn().getTime() - new Date().getTime();
//        long difference_In_Days = (difference_In_Time / (1000 * 60 * 60 * 24)) % 365;
//        System.out.println("difference_In_Days");
//        System.out.println(difference_In_Days);
        return "fdafdsa";
    }

    @GetMapping("subscribe")
    public SseEmitter subscribe() throws IOException {

        SseEmitter emitter = new SseEmitter();

        sseService.add(emitter);
        emitter.onCompletion(() -> sseService.remove(emitter));

        return emitter;
    }

    @GetMapping("producer/test/{data}")
    public String produce(@PathVariable String data) {

        sseService.getSsEmitters().forEach((SseEmitter emitter) -> {
            try {
                emitter.send(data, MediaType.APPLICATION_JSON);
            } catch (IOException e) {
                emitter.complete();
                sseService.remove(emitter);
                e.printStackTrace();
            }
        });
        return data;
    }
}
