package ru.spellsofenglish.player.service.player;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.spellsofenglish.player.dto.player.DataPlayerDto;
import ru.spellsofenglish.player.dto.player.PlayerDto;
import ru.spellsofenglish.player.entity.Player;
import ru.spellsofenglish.player.mapper.PlayerMapperDto;
import ru.spellsofenglish.player.repository.PlayerRepository;
import ru.spellsofenglish.player.service.progress.ProgressService;
import ru.spellsofenglish.player.service.setting.SettingService;


@Service
public class PlayerServiceImpl implements PlayerService {
    private final PlayerMapperDto playerMapperDto;
    private final PlayerRepository playerRepository;
    private final SettingService settingService;
    private final ProgressService progressService;

    @Autowired
    public PlayerServiceImpl(PlayerMapperDto playerMapperDto, PlayerRepository playerRepository, SettingService settingService, ProgressService progressService) {
        this.playerMapperDto = playerMapperDto;
        this.playerRepository = playerRepository;
        this.settingService = settingService;
        this.progressService = progressService;
    }

    @Override
    public DataPlayerDto getPlayer(String username) {
        return playerRepository.findByUsername(username)
                .map(playerMapperDto)
                .orElseThrow(() -> new IllegalArgumentException("User not fount"));
    }

    @Override
    @Transactional
    public void savePlayer(PlayerDto playerDto) {
        var setting = settingService.defaultSetting();
        var progress = progressService.defaultProgress();
        var player = Player.builder()
                .username(playerDto.username())
                .points(50)
                .progress(progress)
                .settings(setting)
                .build();
        playerRepository.save(player);
    }
}
