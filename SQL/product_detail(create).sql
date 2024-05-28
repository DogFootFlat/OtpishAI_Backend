CREATE TABLE `product_detail` (
  `product_detail_code` varchar(255) NOT NULL,
  `product_num` int DEFAULT NULL,
  `product_color` varchar(255) DEFAULT NULL,
  `product_size` varchar(255) DEFAULT NULL,
  `product_inven` int DEFAULT '0',
  `O_price` int DEFAULT '0',
  `R_price` int DEFAULT '0',
  PRIMARY KEY (`product_detail_code`),
  KEY `productCode_idx` (`product_num`),
  CONSTRAINT `ProductCode` FOREIGN KEY (`product_num`) REFERENCES `product` (`product_num`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
