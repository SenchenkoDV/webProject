-- --------------------------------------------------------
-- Хост:                         127.0.0.1
-- Версия сервера:               10.1.31-MariaDB - mariadb.org binary distribution
-- Операционная система:         Win32
-- HeidiSQL Версия:              9.5.0.5196
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Дамп структуры базы данных monsters
CREATE DATABASE IF NOT EXISTS `monsters` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_bin */;
USE `monsters`;

-- Дамп структуры для таблица monsters.comments
CREATE TABLE IF NOT EXISTS `comments` (
  `id_comment` int(11) NOT NULL AUTO_INCREMENT,
  `date` datetime DEFAULT NULL,
  `id_monster` int(11) NOT NULL,
  `mark` int(11) DEFAULT NULL,
  `comment` varchar(45) CHARACTER SET utf8 DEFAULT NULL,
  `id_user` int(11) NOT NULL,
  PRIMARY KEY (`id_comment`,`id_user`,`id_monster`),
  KEY `fk_Comments_Monsters1_idx` (`id_monster`),
  KEY `fk_Ratings_User1_idx` (`id_user`),
  CONSTRAINT `fk_Comments_Monsters1` FOREIGN KEY (`id_monster`) REFERENCES `monsters` (`id_monster`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Ratings_User1` FOREIGN KEY (`id_user`) REFERENCES `users` (`id_user`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- Дамп данных таблицы monsters.comments: ~7 rows (приблизительно)
/*!40000 ALTER TABLE `comments` DISABLE KEYS */;
INSERT INTO `comments` (`id_comment`, `date`, `id_monster`, `mark`, `comment`, `id_user`) VALUES
	(1, '2018-10-10 00:00:00', 1, 5, 'nice', 0),
	(2, '2018-10-11 00:00:00', 2, 7, 'not bad', 0),
	(3, '2018-10-30 00:00:00', 3, 10, 'cool', 0),
	(7, '2018-12-18 00:00:00', 1, 8, 'good hero', 0),
	(8, '2018-12-18 00:00:00', 1, 8, 'good hero', 1),
	(9, '2018-12-18 00:00:00', 1, 8, 'good hero', 1),
	(10, '2018-12-18 00:00:00', 1, 8, 'good', 1);
/*!40000 ALTER TABLE `comments` ENABLE KEYS */;

-- Дамп структуры для таблица monsters.monsters
CREATE TABLE IF NOT EXISTS `monsters` (
  `id_monster` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) CHARACTER SET utf8 DEFAULT NULL,
  `id_race` int(11) NOT NULL,
  `description` varchar(900) CHARACTER SET utf8 DEFAULT NULL,
  `average_rating` varchar(45) CHARACTER SET utf8 DEFAULT NULL,
  PRIMARY KEY (`id_monster`,`id_race`),
  KEY `fk_Monsters_Races1_idx` (`id_race`),
  CONSTRAINT `fk_Monsters_Races1` FOREIGN KEY (`id_race`) REFERENCES `races` (`id_race`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- Дамп данных таблицы monsters.monsters: ~4 rows (приблизительно)
/*!40000 ALTER TABLE `monsters` DISABLE KEYS */;
INSERT INTO `monsters` (`id_monster`, `name`, `id_race`, `description`, `average_rating`) VALUES
	(1, 'Captain Marvel', 1, 'Captain Marvel (Mar-Vell) is a fictional superhero appearing in American comic books published by Marvel Comics. The character was created by writer-editor Stan Lee and designed by artist Gene Colan and first appeared in Marvel Super-Heroes #12 (December 1967).\r\n\r\nThe character debuted during the Silver Age of comic books and has made many appearances since then, including a self-titled series and the second volume of the Marvel Spotlight series. Captain Marvel was ranked 24th in IGN\'s list of "The Top 50 Avengers"[1] and has appeared in television series and video games.\r\n\r\nJude Law will portray Mar-Vell in the Marvel Studios film Captain Marvel, to be released in 2019.', NULL),
	(2, 'Ronan the Accuser', 1, 'Ronan the Accuser is a fictional character appearing in American comic books published by Marvel Comics. He is the Supreme Accuser of the Kree Empire, the militaristic government of the fictional alien race known as the Kree, and is commonly depicted as an adversary of superhero teams such as the Fantastic Four, the Avengers, and the Guardians of the Galaxy.\r\n\r\nThe character has been substantially adapted from the comics into various forms of media, including several animated television series and video games. Most notably, actor Lee Pace portrays Ronan in the live-action Marvel Cinematic Universe films Guardians of the Galaxy and Captain Marvel.', NULL),
	(3, 'Korg', 2, 'Korg is a fictional character appearing in American comic books published by Marvel Comics. Created by writer Greg Pak and artist Carlo Pagulayan, the character first appeared in Incredible Hulk vol. 2 #93 during the "Planet Hulk" storyline.\r\n\r\nKorg appears in Thor: Ragnarok, portrayed by director Taika Waititi through the use of motion capture.', NULL),
	(4, 'Gamora', 3, 'Gamora Zen Whoberi Ben Titan (/ɡəˈmɔːrə/) is a fictional character appearing in American comic books published by Marvel Comics. Created by writer/artist Jim Starlin, the character first appeared in Strange Tales #180 (June 1975). Gamora is the adopted daughter of Thanos, and the last of her species. Her powers include superhuman strength and agility and an accelerated healing factor. She also is an elite combatant, being able to beat most of the opponents in the galaxy. She is a member of the group known as the Infinity Watch. The character played a role in the 2007 crossover comic book event "Annihilation: Conquest", and became a member of the titular team in its spin-off comic, Guardians of the Galaxy.', NULL);
/*!40000 ALTER TABLE `monsters` ENABLE KEYS */;

-- Дамп структуры для таблица monsters.races
CREATE TABLE IF NOT EXISTS `races` (
  `id_race` int(11) NOT NULL AUTO_INCREMENT,
  `race` varchar(45) CHARACTER SET utf8 DEFAULT NULL,
  PRIMARY KEY (`id_race`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- Дамп данных таблицы monsters.races: ~3 rows (приблизительно)
/*!40000 ALTER TABLE `races` DISABLE KEYS */;
INSERT INTO `races` (`id_race`, `race`) VALUES
	(1, 'Kree'),
	(2, 'Kronan'),
	(3, 'Zen-Whoberis');
/*!40000 ALTER TABLE `races` ENABLE KEYS */;

-- Дамп структуры для таблица monsters.roles
CREATE TABLE IF NOT EXISTS `roles` (
  `id_role` int(11) NOT NULL AUTO_INCREMENT,
  `role` varchar(45) CHARACTER SET utf8 DEFAULT NULL,
  PRIMARY KEY (`id_role`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- Дамп данных таблицы monsters.roles: ~2 rows (приблизительно)
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` (`id_role`, `role`) VALUES
	(1, 'admin'),
	(2, 'user');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;

-- Дамп структуры для таблица monsters.users
CREATE TABLE IF NOT EXISTS `users` (
  `id_user` int(11) NOT NULL AUTO_INCREMENT,
  `id_role` int(11) NOT NULL,
  `rating` int(11) DEFAULT NULL,
  `login` varchar(45) CHARACTER SET utf8 DEFAULT NULL,
  `password` varchar(45) CHARACTER SET utf8 DEFAULT NULL,
  `email` varchar(45) CHARACTER SET utf8 DEFAULT NULL,
  PRIMARY KEY (`id_user`,`id_role`),
  KEY `fk_User_Role1_idx` (`id_role`),
  CONSTRAINT `fk_User_Role1` FOREIGN KEY (`id_role`) REFERENCES `roles` (`id_role`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- Дамп данных таблицы monsters.users: ~14 rows (приблизительно)
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` (`id_user`, `id_role`, `rating`, `login`, `password`, `email`) VALUES
	(1, 1, NULL, 'admin', 'adminPass', 'admin@gmail.com'),
	(2, 2, NULL, 'user', 'userPass', 'user@gmail.com'),
	(3, 2, 0, 'ussssser', '12345', 'emaiiiiil'),
	(4, 2, 0, 'ussssser', '12345', 'emaiiiiil'),
	(5, 2, 0, 'user2', 'pass2', 'email2'),
	(6, 2, 0, 'user3', 'pass3', 'email3'),
	(7, 2, 0, 'user4', 'pass4', 'email4'),
	(8, 2, 0, 'user6', 'pass6', 'email6'),
	(9, 2, 0, 'user8', 'pass8', 'email8'),
	(10, 2, 0, 'user10', 'pass10', 'email10'),
	(11, 2, 0, 'user11', 'pass11', 'email11'),
	(12, 2, 0, 'user12', 'pass12', 'email12'),
	(13, 2, 0, 'user13', 'pass13', 'email13'),
	(14, 2, 0, 'user14', 'pass14', 'email14');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
