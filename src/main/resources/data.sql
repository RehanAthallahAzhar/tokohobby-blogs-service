-- Insert Blog Categories
-- Using INSERT ... ON CONFLICT for PostgreSQL to prevent duplicates
-- Table name is "categories" (plural) as per @Table annotation
INSERT INTO categories (name, slug) VALUES ('Technology', 'technology') ON CONFLICT (name) DO NOTHING;
INSERT INTO categories (name, slug) VALUES ('Hobbies', 'hobbies') ON CONFLICT (name) DO NOTHING;
INSERT INTO categories (name, slug) VALUES ('Reviews', 'reviews') ON CONFLICT (name) DO NOTHING;
INSERT INTO categories (name, slug) VALUES ('Tutorials', 'tutorials') ON CONFLICT (name) DO NOTHING;
INSERT INTO categories (name, slug) VALUES ('News', 'news') ON CONFLICT (name) DO NOTHING;
INSERT INTO categories (name, slug) VALUES ('DIY & Crafts', 'diy-crafts') ON CONFLICT (name) DO NOTHING;
INSERT INTO categories (name, slug) VALUES ('Collections', 'collections') ON CONFLICT (name) DO NOTHING;
INSERT INTO categories (name, slug) VALUES ('Gaming', 'gaming') ON CONFLICT (name) DO NOTHING;
INSERT INTO categories (name, slug) VALUES ('Photography', 'photography') ON CONFLICT (name) DO NOTHING;
INSERT INTO categories (name, slug) VALUES ('Lifestyle', 'lifestyle') ON CONFLICT (name) DO NOTHING;

-- Insert Blog Tags
-- Table name is "tags" (plural) as per @Table annotation
INSERT INTO tags (name, slug) VALUES ('action-figure', 'action-figure') ON CONFLICT (name) DO NOTHING;
INSERT INTO tags (name, slug) VALUES ('gundam', 'gundam') ON CONFLICT (name) DO NOTHING;
INSERT INTO tags (name, slug) VALUES ('model-kit', 'model-kit') ON CONFLICT (name) DO NOTHING;
INSERT INTO tags (name, slug) VALUES ('bandai', 'bandai') ON CONFLICT (name) DO NOTHING;
INSERT INTO tags (name, slug) VALUES ('hot-toys', 'hot-toys') ON CONFLICT (name) DO NOTHING;
INSERT INTO tags (name, slug) VALUES ('nendoroid', 'nendoroid') ON CONFLICT (name) DO NOTHING;
INSERT INTO tags (name, slug) VALUES ('figma', 'figma') ON CONFLICT (name) DO NOTHING;
INSERT INTO tags (name, slug) VALUES ('marvel', 'marvel') ON CONFLICT (name) DO NOTHING;
INSERT INTO tags (name, slug) VALUES ('dc-comics', 'dc-comics') ON CONFLICT (name) DO NOTHING;
INSERT INTO tags (name, slug) VALUES ('star-wars', 'star-wars') ON CONFLICT (name) DO NOTHING;
INSERT INTO tags (name, slug) VALUES ('anime', 'anime') ON CONFLICT (name) DO NOTHING;
INSERT INTO tags (name, slug) VALUES ('manga', 'manga') ON CONFLICT (name) DO NOTHING;
INSERT INTO tags (name, slug) VALUES ('board-game', 'board-game') ON CONFLICT (name) DO NOTHING;
INSERT INTO tags (name, slug) VALUES ('card-game', 'card-game') ON CONFLICT (name) DO NOTHING;
INSERT INTO tags (name, slug) VALUES ('puzzle', 'puzzle') ON CONFLICT (name) DO NOTHING;
INSERT INTO tags (name, slug) VALUES ('hobby', 'hobby') ON CONFLICT (name) DO NOTHING;
INSERT INTO tags (name, slug) VALUES ('collection', 'collection') ON CONFLICT (name) DO NOTHING;
INSERT INTO tags (name, slug) VALUES ('review', 'review') ON CONFLICT (name) DO NOTHING;
INSERT INTO tags (name, slug) VALUES ('tutorial', 'tutorial') ON CONFLICT (name) DO NOTHING;
INSERT INTO tags (name, slug) VALUES ('unboxing', 'unboxing') ON CONFLICT (name) DO NOTHING;
INSERT INTO tags (name, slug) VALUES ('photography', 'photography') ON CONFLICT (name) DO NOTHING;
INSERT INTO tags (name, slug) VALUES ('diy', 'diy') ON CONFLICT (name) DO NOTHING;
INSERT INTO tags (name, slug) VALUES ('crafts', 'crafts') ON CONFLICT (name) DO NOTHING;
INSERT INTO tags (name, slug) VALUES ('painting', 'painting') ON CONFLICT (name) DO NOTHING;
INSERT INTO tags (name, slug) VALUES ('customizing', 'customizing') ON CONFLICT (name) DO NOTHING;
INSERT INTO tags (name, slug) VALUES ('tips-tricks', 'tips-tricks') ON CONFLICT (name) DO NOTHING;
INSERT INTO tags (name, slug) VALUES ('beginner-guide', 'beginner-guide') ON CONFLICT (name) DO NOTHING;
INSERT INTO tags (name, slug) VALUES ('advanced', 'advanced') ON CONFLICT (name) DO NOTHING;
INSERT INTO tags (name, slug) VALUES ('limited-edition', 'limited-edition') ON CONFLICT (name) DO NOTHING;
INSERT INTO tags (name, slug) VALUES ('exclusive', 'exclusive') ON CONFLICT (name) DO NOTHING;
INSERT INTO tags (name, slug) VALUES ('pre-order', 'pre-order') ON CONFLICT (name) DO NOTHING;
INSERT INTO tags (name, slug) VALUES ('new-release', 'new-release') ON CONFLICT (name) DO NOTHING;
INSERT INTO tags (name, slug) VALUES ('retro', 'retro') ON CONFLICT (name) DO NOTHING;
INSERT INTO tags (name, slug) VALUES ('vintage', 'vintage') ON CONFLICT (name) DO NOTHING;
INSERT INTO tags (name, slug) VALUES ('miniature', 'miniature') ON CONFLICT (name) DO NOTHING;
INSERT INTO tags (name, slug) VALUES ('scale-model', 'scale-model') ON CONFLICT (name) DO NOTHING;
INSERT INTO tags (name, slug) VALUES ('diorama', 'diorama') ON CONFLICT (name) DO NOTHING;
INSERT INTO tags (name, slug) VALUES ('gaming', 'gaming') ON CONFLICT (name) DO NOTHING;
INSERT INTO tags (name, slug) VALUES ('esports', 'esports') ON CONFLICT (name) DO NOTHING;
INSERT INTO tags (name, slug) VALUES ('cosplay', 'cosplay') ON CONFLICT (name) DO NOTHING;
