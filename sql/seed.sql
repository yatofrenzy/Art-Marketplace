-- Art Marketplace Seed Data

USE art_marketplace;

SET FOREIGN_KEY_CHECKS = 0;

DELETE FROM order_items;
DELETE FROM orders;
DELETE FROM cart_items;
DELETE FROM artworks;
DELETE FROM categories;
DELETE FROM users;

ALTER TABLE order_items AUTO_INCREMENT = 1;
ALTER TABLE orders AUTO_INCREMENT = 1;
ALTER TABLE cart_items AUTO_INCREMENT = 1;
ALTER TABLE artworks AUTO_INCREMENT = 1;
ALTER TABLE categories AUTO_INCREMENT = 1;
ALTER TABLE users AUTO_INCREMENT = 1;

SET FOREIGN_KEY_CHECKS = 1;

-- Admin password = 123456789
-- Customer Password = 123456789
INSERT INTO users (user_id, name, email, password, role, contact_number, profile_image, account_status, phone) VALUES
(1, 'Admin', 'admin@gmail.com', '123', '$2a$10$6cFa/jsQoPgXYs5kcSaQAuS3GMe2QEWDHUmqzW0Ib7nKTtDqwwk9C', NULL, NULL, 'Approved', '9800000000'),
(2, 'Customer', 'customer@gmail.com', '$2a$10$k0clZAroNJK4kD4LC97Szear2qqmsBFv8ZQbx38dbsuZO/9lI3vm.', 'customer', '9819821233', NULL, 'Approved', '9819821233'),
(3, 'Asmi', 'asmi@gmail.com', '$2a$10$M42roEiNaWR8TCfFN73kme/3u4g.75A1gDZQ1ScmzS4SofEoCKVKa', 'customer', NULL, NULL, 'Approved', '9812340983'),
(4, 'Nabraj', 'nabraj@gmail.com', '$2a$10$gGZX7Z/X6Cim4.z/09VdneuRvL5Ya7Yvc.ofunygfeTrc.8R.Qreu', 'customer', NULL, NULL, 'Pending', '9812340912'),
(5, 'Samrat', 'samrat@gmail.com', '$2a$10$gGZX7Z/X6Cim4.z/09VdneuRvL5Ya7Yvc.ofunygfeTrc.8R.Qreu', 'customer', NULL, NULL, 'Pending', '9812340913'),
(6, 'Sudeshna', 'sudeshna@gmail.com', '$2a$10$NQlPuARk98ZQ/ba1R.lP3.P6Xcu5Tk4RGGIzbcDf9Dqoo9TJRbChG', 'customer', NULL, NULL, 'Pending', '9812340914');

INSERT INTO categories (category_id, category_name) VALUES
(1, 'Painting'),
(2, 'Sketch'),
(3, 'Digital Art'),
(6, 'Nature Art'),
(7, 'Portrait');

INSERT INTO artworks (artwork_id, category_id, title, description, price, image_path) VALUES
(9, 6, 'Whispers of the Silent Peak', 'A peaceful winter landscape where snow-covered mountains fade into soft mist. Tall pine trees stand quietly along a narrow path, creating a calm and meditative atmosphere.', 25000.00, 'resources/images/Nature-Art/Whispers of the Silent Peak.jpg'),
(10, 6, 'Golden Canopy Dream', 'A vibrant forest filled with tall trees covered in rich golden leaves. Sunlight filters through the canopy, creating a warm and magical autumn environment.', 10000.00, 'resources/images/Nature-Art/Golden Canopy Dream.jpg'),
(11, 6, 'Echoes in the Blue Forest', 'Layers of deep blue trees stretch into a misty horizon, creating a mysterious and dreamlike scene with a sense of depth and quiet beauty.', 20000.00, 'resources/images/Nature-Art/Echoes in the Blue Forest.jpg'),
(12, 6, 'Harmony of the Hidden Grove', 'A lush green forest filled with detailed trees and graceful birds. The artwork captures a peaceful natural ecosystem full of life and balance.', 24000.00, 'resources/images/Nature-Art/Harmony of the Hidden Grove.jpg'),
(37, 1, 'Composition VII', 'The work is considered a combination of several spiritual themes, including the Resurrection, the Last Judgment, the Flood, and the Garden of Eden.', 2000.00, 'resources/images/Paintings/composition VII.jpg'),
(38, 1, 'Demi Lovato', 'This striking original painting captures a powerful narrative of internal struggle and transformation. Using a mix of warm and cool tones, the piece portrays a duality of self.', 28000.00, 'resources/images/Paintings/demi loavto oil painting.png'),
(39, 1, 'Arctic Fox in Twilight', 'This original watercolor painting captures the serene beauty of an Arctic fox as it moves gracefully through a sun-drenched landscape.', 26000.00, 'resources/images/Paintings/fox watercolor.png'),
(40, 1, 'Crown of Thorns', 'Step into a moment of profound spiritual transcendence with this breathtaking depiction of the Passion.', 32000.00, 'resources/images/Paintings/jesus enamel painting.png'),
(46, 3, 'Erró', 'Erró is a prodigy of visual shock, which he creates through the assembly of powerful symbols.', 34000.00, 'resources/images/Digital_Art/Erró.png'),
(47, 3, 'Sarah Shakeel', 'Sarah Shakeel’s works translate provocative humor amongst a glittery poetic background.', 24000.00, 'resources/images/Digital_Art/Sarah Shakeel.png'),
(48, 3, 'La Robotte', 'An incredibly dualistic and surrealist approach. Anchored in concrete life yet floating in a dreamlike universe.', 36000.00, 'resources/images/Digital_Art/La Robotte.png'),
(49, 3, 'Valentin Pavageau', 'Alive and surprising, digital art gives rise every day to new artists blooming in this contemporary landscape.', 12000.00, 'resources/images/Digital_Art/Valentin Pavageau.png'),
(50, 3, 'Jean-Marie Gitard', 'Jean-Marie Gitard mixes and diverts artistic codes, political references and strong symbols through a surrealist approach.', 8000.00, 'resources/images/Digital_Art/Jean-Marie Gitard.png'),
(57, 2, 'Cartoon Character Sketch', 'A creative sketch of an animated character featuring bold outlines and expressive facial features.', 10000.00, 'resources/images/Sketch/cartoon sketch.png'),
(58, 2, 'Lady with the Balloon', 'A delicate pencil sketch of a woman holding a red balloon. The contrast creates a sense of emotion and freedom.', 12000.00, 'resources/images/Sketch/lady with the ballon.png'),
(59, 2, 'Human Expression Study I', 'A detailed human sketch focusing on facial structure and expression.', 8000.00, 'resources/images/Sketch/image (1).png'),
(60, 2, 'Human Expression Study II', 'A refined sketch of a human figure emphasizing light and shadow.', 2000.00, 'resources/images/Sketch/image (2).png'),
(61, 2, 'Human Expression Study III', 'A realistic human portrait sketch capturing intricate details of the eyes and facial features.', 1000.00, 'resources/images/Sketch/image (3).png'),
(62, 2, 'Human Expression Study IV', 'A finely drawn human sketch showcasing advanced shading and proportion.', 12000.00, 'resources/images/Sketch/image (4).png'),
(63, 7, 'Veil of Light and Silence', 'A graceful woman stands beneath an archway as soft light wraps around her flowing white dress. The motion of fabric blends with light, creating a dreamlike feeling of calm, elegance, and quiet strength.', 18000.00, 'resources/images/Portrait/veil_light_silence.jpg'),
(64, 7, 'Falling Into Reflection', 'A woman floats gently above a deep dark circle in shimmering water. The ripples of light and shadow create a surreal moment, symbolizing introspection, depth, and emotional surrender.', 17000.00, 'resources/images/Portrait/falling_reflection.jpg'),
(65, 7, 'Serenity Beneath the Surface', 'Bathed in soft underwater light, a peaceful figure drifts in silence. The glowing reflections and gentle posture express calmness, vulnerability, and inner peace.', 16000.00, 'resources/images/Portrait/serenity_surface.jpg'),
(66, 7, 'Dance of Light and Water', 'Surrounded by rippling reflections, a woman moves gracefully as light dances across her dress. The scene captures motion, fluidity, and the harmony between body and water.', 17500.00, 'resources/images/Portrait/dance_light_water.jpg');

ALTER TABLE artworks AUTO_INCREMENT = 67;
ALTER TABLE categories AUTO_INCREMENT = 8;
ALTER TABLE users AUTO_INCREMENT = 5;
