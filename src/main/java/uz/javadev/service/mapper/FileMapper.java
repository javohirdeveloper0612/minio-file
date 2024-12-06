package uz.javadev.service.mapper;

import org.mapstruct.Mapper;
import uz.javadev.domain.FileEntity;
import uz.javadev.service.dto.FileDto;

@Mapper(componentModel = "spring")
public interface FileMapper extends EntityMapper<FileDto, FileEntity> {
}
