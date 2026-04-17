USE art_marketplace;

-- USERS
INSERT INTO users (name,email,password,role) VALUES
('Admin','admin@gmail.com','123','admin'),
('Asmi','asmi@gmail.com','123','customer'),
('Sita','sita@gmail.com','123','artist'),
('Ram','ram@gmail.com','123','artist'),
('Hari','hari@gmail.com','123','customer');

-- CATEGORIES
INSERT INTO categories (category_name) VALUES
('Painting'),('Sketch'),('Digital'),('Photography'),('Abstract');

-- ARTWORKS
INSERT INTO artworks (user_id,category_id,title,price,status) VALUES
(3,1,'Sunset',5000,'Approved'),
(3,2,'Portrait',2000,'Approved'),
(4,3,'Cyber Art',7000,'Pending'),
(4,4,'Nature Photo',3000,'Approved'),
(3,5,'Dream Art',4000,'Rejected');

-- CART
INSERT INTO cart_items (user_id,artwork_id,quantity) VALUES
(2,1,1),
(2,2,2),
(5,4,1);

-- ORDERS
INSERT INTO orders (user_id,total_amount,payment_method,payment_status) VALUES
(2,9000,'Esewa','Paid'),
(5,3000,'Cash','Pending');

-- ORDER ITEMS
INSERT INTO order_items (order_id,artwork_id,quantity,price) VALUES
(1,1,1,5000),
(1,2,2,2000),
(2,4,1,3000);