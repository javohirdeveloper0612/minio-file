package uz.retail.core.service.mapper;

import org.mapstruct.Mapper;
import uz.retail.core.domain.FileManagement;
import uz.retail.core.service.dto.FileManagementDTO;

@Mapper(componentModel = "spring")
public interface FileManagementMapper extends EntityMapper<FileManagementDTO, FileManagement> {
}
