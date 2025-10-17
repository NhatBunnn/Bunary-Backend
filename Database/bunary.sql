-- Set default learning settings for existing users (only if not already set)

INSERT INTO bunary.settings (user_id, type, settings)
SELECT 
    u.id,
    'learning',
    JSON_OBJECT(
        'mode', 'flashcard',
        'front', JSON_OBJECT(
            'term', TRUE,
            'ipa', FALSE,
            'meaning', FALSE,
            'partOfSpeech', FALSE,
            'thumbnail', TRUE
        ),
        'back', JSON_OBJECT(
            'term', FALSE,
            'ipa', TRUE,
            'meaning', TRUE,
            'partOfSpeech', TRUE,
            'thumbnail', FALSE
        )
    )
FROM bunary.users u
WHERE NOT EXISTS (
    SELECT 1
    FROM bunary.settings s
    WHERE s.user_id = u.id
);
