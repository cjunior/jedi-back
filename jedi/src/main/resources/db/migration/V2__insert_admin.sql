INSERT INTO users (
    id, login, password, name, created_at, role, photo_url
) VALUES (
             gen_random_uuid(),
             'admin@example.com',
             '$2a$10$wnVRc9BG3rmcnvQU1BVZU.kEHLHExG2f/fiKlb9.Chmxs9NFxcNpq',
             'Admin',
             now(),
             'ADMIN',
             null
         );