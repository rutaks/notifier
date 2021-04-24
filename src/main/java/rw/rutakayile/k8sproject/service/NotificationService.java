package rw.rutakayile.k8sproject.service;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

public interface NotificationService {

    boolean add(SseEmitter sseEmitter);

    boolean remove(SseEmitter sseEmitter);

    List<SseEmitter> getSsEmitters();

}
