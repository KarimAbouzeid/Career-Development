package com.example.demo.mappers;

import com.example.demo.dtos.NotificationsDTO;
import com.example.demo.entities.Notifications;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface NotificationMapper {

    @Mapping(source = "notificationData.id", target = "notification_data_id")
    NotificationsDTO toNotificationsDTO(Notifications notifications);

    @Mapping(source = "notification_data_id", target = "notificationData.id")
    Notifications toNotifications(NotificationsDTO notificationsDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateNotificationsFromDTO(NotificationsDTO notificationsDTO, @MappingTarget Notifications notifications);
}
