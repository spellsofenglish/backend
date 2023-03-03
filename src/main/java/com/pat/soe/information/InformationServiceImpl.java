package com.pat.soe.information;

import com.pat.soe.user.exception.NotFoundException;
import com.pat.soe.user.exception.SoeException;
import com.pat.soe.user.InternalizationMessageManagerConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service(value = "informationService")
@RequiredArgsConstructor
public class InformationServiceImpl implements InformationService {
    private final InformationRepository informationRepository;
    private final InformationMapper informationMapper;

    public static final String USER_S_IS_NOT_FOUND = "User %s is not found";
    public static final String KET_FOR_EMAIL_RECOVERY_PASSWORD_SUBJECT = "UserService.EmailRecoveryPasswordSubject";
    public static final String KEY_FOR_EXCEPTION_INFORMATION_NOT_FOUND = "informationService.informationNotFound";
    public static final String KEY_FOR_EXCEPTION_ACTIVATE_LINK_PATTERN = "UserService.ActivateLinkPattern";
    public static final String KEY_FOR_EMAIL_USER_CONFIRMATION_SUBJECT = "UserService.UserConfirmationSubject";
    public static final String KEY_FOR_RECOVERY_PASS_LINK_PATTERN = "UserService.RecoveryPassLinkPattern";
    public static final String KEY_FOR_EXCEPTION_WRONG_OLD_PASSWORD = "UserService.WrongOldPassword";
    public static final String KEY_FOR_EXCEPTION_USER_NOT_ACTIVATED = "UserService.UserNotActivated";
    @Override
    public InformationDto getById(Long id) {
        Information information = informationRepository.findById(id)
                .orElseThrow(() -> new InformationNotFoundException(InformationInternalizationMessageManagerConfig
                        .getExceptionMessage(KEY_FOR_EXCEPTION_INFORMATION_NOT_FOUND)));
        return informationMapper.informationToInformationDto(information);
    }

    @Override
    public Page<InformationDto> getAll(Pageable pageable) {
        Page<Information> users = informationRepository.findAll(pageable);
        return users.map(informationMapper::informationToInformationDto);
    }

    @Override
    public InformationDto create(InformationDtoForSave dtoForSave) {
        Information entity = informationMapper.informationDtoForSaveToInformation(dtoForSave);
        entity.setActive(true);
        Information created = informationRepository.save(entity);
        return informationMapper.informationToInformationDto(created);
    }

    @Override
    public InformationDto update(InformationDto dto) {
        Optional<Information> existing = informationRepository.findById(dto.getId());
        if (existing.isPresent() && !existing.get().getId().equals(dto.getId())) {
            throw new UserException(String.format(InformationInternalizationMessageManagerConfig
                    .getExceptionMessage(KEY_FOR_EXCEPTION_INFORMATION_NOT_FOUND), dto.getName()));
        }
        Information created = informationMapper.informationDtoToInformation(dto);
        Information updated = informationRepository.save(created);
        return informationMapper.informationToInformationDto(updated);
    }

    @Override
    public void delete(Long id) {
        Information information = informationRepository.findById(id)
                .orElseThrow(() -> new InformationNotFoundException(InformationInternalizationMessageManagerConfig
                        .getExceptionMessage(KEY_FOR_EXCEPTION_INFORMATION_NOT_FOUND)));
        if (!information.isActive()) {
            throw new InformationNotFoundException(InformationInternalizationMessageManagerConfig
                    .getExceptionMessage(KEY_FOR_EXCEPTION_INFORMATION_NOT_FOUND));
        }
        information.setActive(false);
        informationRepository.save(information);
    }
}
