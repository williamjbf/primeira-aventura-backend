-- =====================
-- USUÁRIOS
-- =====================
INSERT INTO users (created_at, deleted_at, updated_at, email, password, username)
SELECT '2025-08-26', null, '2025-08-26', 'alice@example.com', '$2a$10$7Qe2vVx0q1D1H7lQwW8pO.N8AxB6/6FoRZZwXxX/0eY6xQpNlEvG6', 'Alice'
WHERE NOT EXISTS (SELECT 1 FROM users WHERE email='alice@example.com');

INSERT INTO users (created_at, deleted_at, updated_at, email, password, username)
SELECT '2025-08-26', null, '2025-08-26', 'bob@example.com', '$2a$10$Vb8pE3jW6Nz5R1QkL5H1QuhM4W6B1xY2lFq2g1Qw5Zp9E6XnUo7yC', 'Bob'
WHERE NOT EXISTS (SELECT 1 FROM users WHERE email='bob@example.com');

-- =====================
-- TAGS
-- =====================
INSERT INTO tags (nome)
SELECT 'Fantasia' WHERE NOT EXISTS (SELECT 1 FROM tags WHERE nome='Fantasia');

INSERT INTO tags (nome)
SELECT 'Sci-Fi' WHERE NOT EXISTS (SELECT 1 FROM tags WHERE nome='Sci-Fi');

INSERT INTO tags (nome)
SELECT 'Terror' WHERE NOT EXISTS (SELECT 1 FROM tags WHERE nome='Terror');

INSERT INTO tags (nome)
SELECT 'Cyberpunk' WHERE NOT EXISTS (SELECT 1 FROM tags WHERE nome='Cyberpunk');

INSERT INTO tags (nome)
SELECT 'Medieval' WHERE NOT EXISTS (SELECT 1 FROM tags WHERE nome='Medieval');

INSERT INTO tags (nome)
SELECT 'Aventura' WHERE NOT EXISTS (SELECT 1 FROM tags WHERE nome='Aventura');

INSERT INTO tags (nome)
SELECT 'Horror' WHERE NOT EXISTS (SELECT 1 FROM tags WHERE nome='Horror');

INSERT INTO tags (nome)
SELECT 'Steampunk' WHERE NOT EXISTS (SELECT 1 FROM tags WHERE nome='Steampunk');

INSERT INTO tags (nome)
SELECT 'Histórico' WHERE NOT EXISTS (SELECT 1 FROM tags WHERE nome='Histórico');


-- =====================
-- MESAS
-- =====================
INSERT INTO tables (created_at, deleted_at, updated_at, horario, imagem, "local", resumo, sistema, titulo, id_narrador)
SELECT '2025-08-26', null, '2025-08-26', '{"dia":"Sábado","hora":"20:00"}', '/uploads/default.png', 'Sala 1', 'Uma aventura épica em terras mágicas', 'D&D 5e', 'A Jornada de Eldoria', 1
WHERE NOT EXISTS (SELECT 1 FROM tables WHERE titulo='A Jornada de Eldoria');

INSERT INTO tables (created_at, deleted_at, updated_at, horario, imagem, "local", resumo, sistema, titulo, id_narrador)
SELECT '2025-08-26', null, '2025-08-26', '{"dia":"Domingo","hora":"18:00"}', '/uploads/default.png', 'Sala 2', 'Exploração de planetas desconhecidos', 'Starfinder', 'O Mistério de Orion', 2
WHERE NOT EXISTS (SELECT 1 FROM tables WHERE titulo='O Mistério de Orion');

INSERT INTO tables (created_at, deleted_at, updated_at, horario, imagem, "local", resumo, sistema, titulo, id_narrador)
SELECT '2025-08-26', null, '2025-08-26', '{"dia":"Sábado","hora":"22:00"}', '/uploads/default.png', 'Sala 3', 'Mistérios sombrios em uma cidade amaldiçoada', 'Call of Cthulhu', 'Sombras de Arkham', 3
WHERE NOT EXISTS (SELECT 1 FROM tables WHERE titulo='Sombras de Arkham');

INSERT INTO tables (created_at, deleted_at, updated_at, horario, imagem, "local", resumo, sistema, titulo, id_narrador)
SELECT '2025-08-26', null, '2025-08-26', '{"dia":"Domingo","hora":"19:00"}', '/uploads/default.png', 'Sala 4', 'Exploração de tecnologias antigas e vapor', 'GURPS', 'Engrenagens do Destino', 4
WHERE NOT EXISTS (SELECT 1 FROM tables WHERE titulo='Engrenagens do Destino');

INSERT INTO tables (created_at, deleted_at, updated_at, horario, imagem, "local", resumo, sistema, titulo, id_narrador)
SELECT '2025-08-26', null, '2025-08-26', '{"dia":"Sexta","hora":"21:00"}', '/uploads/default.png', 'Sala 5', 'Aventura em reinos medievais com intrigas políticas', 'Pathfinder', 'Conquista de Eldoria', 5
WHERE NOT EXISTS (SELECT 1 FROM tables WHERE titulo='Conquista de Eldoria');

-- =====================
-- MESA ↔ TAGS
-- =====================
INSERT INTO table_rpg_tags (table_rpg_id, tag_id)
SELECT 1, 1 WHERE NOT EXISTS (SELECT 1 FROM table_rpg_tags WHERE table_rpg_id=1 AND tag_id=1);

INSERT INTO table_rpg_tags (table_rpg_id, tag_id)
SELECT 1, 6 WHERE NOT EXISTS (SELECT 1 FROM table_rpg_tags WHERE table_rpg_id=1 AND tag_id=6);

INSERT INTO table_rpg_tags (table_rpg_id, tag_id)
SELECT 2, 2 WHERE NOT EXISTS (SELECT 1 FROM table_rpg_tags WHERE table_rpg_id=2 AND tag_id=2);

INSERT INTO users (created_at, deleted_at, updated_at, email, password, username)
SELECT '2025-08-26', null, '2025-08-26', 'carol@example.com', '$2a$10$AbcD1234EfGh5678IjKlMnOpQrStUvWxYz0123456789abcdEfGh', 'Carol'
WHERE NOT EXISTS (SELECT 1 FROM users WHERE email='carol@example.com');

INSERT INTO users (created_at, deleted_at, updated_at, email, password, username)
SELECT '2025-08-26', null, '2025-08-26', 'dave@example.com', '$2a$10$ZxYwVuTsRqPoNmLkJiHgFeDcBa9876543210lkjHgfEdCbA', 'Dave'
WHERE NOT EXISTS (SELECT 1 FROM users WHERE email='dave@example.com');

INSERT INTO users (created_at, deleted_at, updated_at, email, password, username)
SELECT '2025-08-26', null, '2025-08-26', 'eve@example.com', '$2a$10$LmNoPqRsTuVwXyZaBcDeFgHiJkL1234567890abcdefGhIj', 'Eve'
WHERE NOT EXISTS (SELECT 1 FROM users WHERE email='eve@example.com');

INSERT INTO table_rpg_tags (table_rpg_id, tag_id)
SELECT 3, 3 WHERE NOT EXISTS (SELECT 1 FROM table_rpg_tags WHERE table_rpg_id=3 AND tag_id=3);

INSERT INTO table_rpg_tags (table_rpg_id, tag_id)
SELECT 3, 6 WHERE NOT EXISTS (SELECT 1 FROM table_rpg_tags WHERE table_rpg_id=3 AND tag_id=6);

INSERT INTO table_rpg_tags (table_rpg_id, tag_id)
SELECT 4, 4 WHERE NOT EXISTS (SELECT 1 FROM table_rpg_tags WHERE table_rpg_id=4 AND tag_id=4);

INSERT INTO table_rpg_tags (table_rpg_id, tag_id)
SELECT 4, 2 WHERE NOT EXISTS (SELECT 1 FROM table_rpg_tags WHERE table_rpg_id=4 AND tag_id=2);

INSERT INTO table_rpg_tags (table_rpg_id, tag_id)
SELECT 5, 5 WHERE NOT EXISTS (SELECT 1 FROM table_rpg_tags WHERE table_rpg_id=5 AND tag_id=5);

INSERT INTO table_rpg_tags (table_rpg_id, tag_id)
SELECT 5, 1 WHERE NOT EXISTS (SELECT 1 FROM table_rpg_tags WHERE table_rpg_id=5 AND tag_id=1);

-- =====================
-- INSCRIÇÕES USUÁRIOS ↔ MESAS
-- =====================
INSERT INTO table_subscriptions (created_at, deleted_at, updated_at, status, table_rpg_id, user_id)
SELECT '2025-08-26', null, '2025-08-26', 'PENDENTE', 1, 2
WHERE NOT EXISTS (SELECT 1 FROM table_subscriptions WHERE table_rpg_id=1 AND user_id=2);

INSERT INTO table_subscriptions (created_at, deleted_at, updated_at, status, table_rpg_id, user_id)
SELECT '2025-08-26', null, '2025-08-26', 'ACEITO', 1, 3
WHERE NOT EXISTS (SELECT 1 FROM table_subscriptions WHERE table_rpg_id=1 AND user_id=3);

INSERT INTO table_subscriptions (created_at, deleted_at, updated_at, status, table_rpg_id, user_id)
SELECT '2025-08-26', null, '2025-08-26', 'PENDENTE', 3, 1
WHERE NOT EXISTS (SELECT 1 FROM table_subscriptions WHERE table_rpg_id=3 AND user_id=1);

INSERT INTO table_subscriptions (created_at, deleted_at, updated_at, status, table_rpg_id, user_id)
SELECT '2025-08-26', null, '2025-08-26', 'ACEITO', 3, 2
WHERE NOT EXISTS (SELECT 1 FROM table_subscriptions WHERE table_rpg_id=3 AND user_id=2);

INSERT INTO table_subscriptions (created_at, deleted_at, updated_at, status, table_rpg_id, user_id)
SELECT '2025-08-26', null, '2025-08-26', 'PENDENTE', 4, 3
WHERE NOT EXISTS (SELECT 1 FROM table_subscriptions WHERE table_rpg_id=4 AND user_id=3);

INSERT INTO table_subscriptions (created_at, deleted_at, updated_at, status, table_rpg_id, user_id)
SELECT '2025-08-26', null, '2025-08-26', 'ACEITO', 5, 4
WHERE NOT EXISTS (SELECT 1 FROM table_subscriptions WHERE table_rpg_id=5 AND user_id=4);

INSERT INTO table_subscriptions (created_at, deleted_at, updated_at, status, table_rpg_id, user_id)
SELECT '2025-08-26', null, '2025-08-26', 'PENDENTE', 5, 5
WHERE NOT EXISTS (SELECT 1 FROM table_subscriptions WHERE table_rpg_id=5 AND user_id=5);