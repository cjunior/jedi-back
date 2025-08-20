INSERT INTO users(id, login, password, name, created_at, role, photo_url)
SELECT gen_random_uuid(),
       'admin@generico.com',
       '$2a$10$wnVRc9BG3rmcnvQU1BVZU.kEHLHExG2f/fiKlb9.Chmxs9NFxcNpq',
       'Admin',
       now(),
       'ADMIN',
       null
    WHERE NOT EXISTS (
    SELECT 1 FROM users WHERE login = 'admin@generico.com'
);
