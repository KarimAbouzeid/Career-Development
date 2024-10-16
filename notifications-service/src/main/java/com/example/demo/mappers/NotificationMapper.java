package com.example.demo.mappers;

import com.example.demo.dtos.NotificationDTO;
import com.example.demo.entities.Notifications;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface NotificationMapper {

    @Mapping(source = "notificationData.id", target = "notification_data_id")
    NotificationDTO toNotificationsDTO(Notifications notifications);

    @Mapping(source = "notification_data_id", target = "notificationData.id")
    Notifications toNotifications(NotificationDTO notificationsDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateNotificationsFromDTO(NotificationDTO notificationsDTO, @MappingTarget Notifications notifications);
}
