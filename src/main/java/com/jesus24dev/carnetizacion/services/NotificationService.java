
package com.jesus24dev.carnetizacion.services;

import com.jesus24dev.carnetizacion.models.Employee;
import com.jesus24dev.carnetizacion.models.Notification;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    
    private final SimpMessagingTemplate messagingTemplate;
    
    public NotificationService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }
    
    public void sendGlobalNotification(Notification notification) {
        messagingTemplate.convertAndSend("/topic/notificaciones", notification);
    }
    
    public void notifyNewEmployee(Employee employee) {
        Notification notification = new Notification(
            "NUEVO_CARNET",
            "EL SIGUIENTE EMPLEADO HA SOLICITADO UN CARNET: " + employee.getName(),
            "EMPLEADO",
            employee.getCi()
        );
        notification.setData(employee);
        
        sendGlobalNotification(notification);
    }
    
}
