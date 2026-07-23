TRUNCATE TABLE blogs CASCADE;

INSERT INTO blogs (author_id, content, created_at, deleted, image_path, preview_token, published_at, slug, status, title, updated_at, youtube_link, category_id)
SELECT 
    '00000000-0000-0000-0000-000000000000' AS author_id,
    'Ini adalah konten artikel blog pengujian ke-' || i || '. Artikel ini membahas topik menarik mengenai dunia hobi, koleksi mainan, tips perakitan model kit, dan ulasan game seru. Kami menyajikan informasi berkualitas tinggi bagi seluruh kolektor dan pehobi di Indonesia.' AS content,
    NOW() - (i * INTERVAL '2 hour') AS created_at,
    false AS deleted,
    CASE (i % 5)
        WHEN 0 THEN 'https://images.unsplash.com/photo-1566577134770-3d85bb3a9cc4?auto=format&fit=crop&q=80&w=600'
        WHEN 1 THEN 'https://images.unsplash.com/photo-1587202372775-e229f172b9d7?auto=format&fit=crop&q=80&w=600'
        WHEN 2 THEN 'https://images.unsplash.com/photo-1610890716171-6b1bb98ffd09?auto=format&fit=crop&q=80&w=600'
        WHEN 3 THEN 'https://images.unsplash.com/photo-1534447677768-be436bb09401?auto=format&fit=crop&q=80&w=600'
        ELSE 'https://images.unsplash.com/photo-1608889175123-8ec330b86f84?auto=format&fit=crop&q=80&w=600'
    END AS image_path,
    NULL AS preview_token,
    NOW() - (i * INTERVAL '2 hour') AS published_at,
    'artikel-hobi-kreatif-dan-koleksi-terbaru-' || i AS slug,
    'PUBLISHED' AS status,
    CASE (i % 6)
        WHEN 0 THEN 'Panduan Lengkap Memulai Koleksi Gunpla untuk Pemula ' || i
        WHEN 1 THEN 'Review Detail Action Figure Luffy Gear 5 Kaido Battle ' || i
        WHEN 2 THEN 'Tips Merakit Diorama Meja Belajar dengan Lego Star Wars ' || i
        WHEN 3 THEN 'Sejarah Mini 4WD Tamiya Magnum Saber Terpopuler ' || i
        WHEN 4 THEN '10 Action Figure Hot Toys Paling Bernilai Tinggi ' || i
        ELSE 'Rekomendasi Board Game Terbaik untuk Dimainkan Bersama Keluarga ' || i
    END AS title,
    NOW() - (i * INTERVAL '2 hour') AS updated_at,
    NULL AS youtube_link,
    (1 + (i % 10)) AS category_id
FROM generate_series(1, 100) AS i;

INSERT INTO blog_tags (blog_id, tag_id)
SELECT id, (1 + (id % 20)) AS tag_id FROM blogs
UNION ALL
SELECT id, (2 + ((id + 3) % 20)) AS tag_id FROM blogs;

