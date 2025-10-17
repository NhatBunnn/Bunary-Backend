package com.bunary.vocab.util.setting;

import java.util.HashMap;
import java.util.Map;

import com.bunary.vocab.model.Setting;

public class LearningSettingsFactory {
    public static Setting defaultFlashCard() {
        Setting setting = new Setting();
        setting.setType("learning");

        Map<String, Boolean> front = Map.of(
                "term", true,
                "ipa", false,
                "meaning", false,
                "partOfSpeech", false,
                "thumbnail", true);

        Map<String, Boolean> back = Map.of(
                "term", false,
                "ipa", true,
                "meaning", true,
                "partOfSpeech", true,
                "thumbnail", false);

        Map<String, Object> settings = new HashMap<>();
        settings.put("mode", "flashcard");
        settings.put("front", front);
        settings.put("back", back);

        setting.setSettings(settings);

        return setting;
    }
}
