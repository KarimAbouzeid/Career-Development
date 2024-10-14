package com.example.demo.mappers;

import com.example.demo.dtos.NotificationDataDTO;
import com.example.demo.entities.NotificationData;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface NotificationDataMapper {

    @Mapping(source = "action.id", target = "action_id")
    NotificationDataDTO toNotificationDataDTO(NotificationData notificationData);

    @Mapping(source = "action_id", target = "action.id")
    NotificationData toNotificationData(NotificationDataDTO notificationDataDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateNotificationDataFromDTO(NotificationDataDTO notificationDataDTO, @MappingTarget NotificationData notificationData);
}
