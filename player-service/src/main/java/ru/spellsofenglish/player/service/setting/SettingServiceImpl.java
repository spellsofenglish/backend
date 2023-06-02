package ru.spellsofenglish.player.service.setting;

import org.springframework.stereotype.Service;
import ru.spellsofenglish.player.entity.Settings;

import java.util.UUID;
@Service
public class SettingServiceImpl implements SettingService{

    @Override
    public Settings playerSetting(UUID playerId) {
        return null;
    }
}
