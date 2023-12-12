-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Dec 12, 2023 at 02:16 AM
-- Server version: 10.4.28-MariaDB
-- PHP Version: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `apiclass111`
--

-- --------------------------------------------------------

--
-- Table structure for table `tblcategories`
--

CREATE TABLE `tblcategories` (
  `CategoryID` int(11) NOT NULL,
  `CategoryName` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `Description` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `CreateDate` timestamp NOT NULL DEFAULT current_timestamp(),
  `CreateBy` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `UpdatedDate` datetime NOT NULL,
  `UpdatedBy` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tblcategories`
--

INSERT INTO `tblcategories` (`CategoryID`, `CategoryName`, `Description`, `CreateDate`, `CreateBy`, `UpdatedDate`, `UpdatedBy`) VALUES
(4, 'Fruit', 'For Selling', '2023-09-26 04:35:39', 'vithei', '0000-00-00 00:00:00', NULL),
(5, 'JKBERFR', 'RFLWUEFHWER', '2023-12-10 12:12:16', '23423RWER', '2023-12-10 13:11:59', NULL),
(6, '23432', '423REWRWE', '2023-12-10 12:12:16', '3', '2023-12-10 13:11:59', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `tblcontact`
--

CREATE TABLE `tblcontact` (
  `contactID` int(11) NOT NULL,
  `contactName` varchar(50) NOT NULL,
  `contactNumber` varchar(20) NOT NULL,
  `contactEmail` varchar(100) DEFAULT NULL,
  `contactImage` varchar(50) NOT NULL DEFAULT 'default.png',
  `favorites` int(11) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tblcontact`
--

INSERT INTO `tblcontact` (`contactID`, `contactName`, `contactNumber`, `contactEmail`, `contactImage`, `favorites`) VALUES
(28, 'Daro', '09771918234', 'daro@email.com', 'default.png', 0);

-- --------------------------------------------------------

--
-- Table structure for table `tblproducts`
--

CREATE TABLE `tblproducts` (
  `ProductID` int(11) NOT NULL,
  `ProductName` varchar(50) NOT NULL,
  `Description` varchar(100) DEFAULT NULL,
  `CategoryID` int(11) NOT NULL,
  `Barcode` varchar(50) NOT NULL,
  `ExpiredDate` date DEFAULT NULL,
  `Qty` int(11) NOT NULL,
  `UnitPrice` double NOT NULL,
  `UnitPriceOut` double NOT NULL,
  `ProductImage` varchar(50) NOT NULL DEFAULT 'default.png',
  `isDelete` int(11) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tblproducts`
--

INSERT INTO `tblproducts` (`ProductID`, `ProductName`, `Description`, `CategoryID`, `Barcode`, `ExpiredDate`, `Qty`, `UnitPrice`, `UnitPriceOut`, `ProductImage`, `isDelete`) VALUES
(1, 'Coca', NULL, 6, '123123123', NULL, 123123, 12312, 0, 'default.png', 0),
(2, 'COca', NULL, 6, '234234', NULL, 234, 234234, 32423432, 'default.png', 0),
(3, '99999', NULL, 4, '99999', NULL, 99999, 99999, 99999, 'default.png', 0);

-- --------------------------------------------------------

--
-- Table structure for table `tbluser`
--

CREATE TABLE `tbluser` (
  `UserID` int(11) NOT NULL,
  `UserName` varchar(50) NOT NULL,
  `UserPassword` varchar(100) NOT NULL,
  `FullName` varchar(50) NOT NULL,
  `UserType` varchar(30) NOT NULL DEFAULT 'user',
  `Email` varchar(100) DEFAULT NULL,
  `UserImage` varchar(100) NOT NULL DEFAULT 'default.png'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `tbluser`
--

INSERT INTO `tbluser` (`UserID`, `UserName`, `UserPassword`, `FullName`, `UserType`, `Email`, `UserImage`) VALUES
(1, 'bbu1', '202cb962ac59075b964b07152d234b70', 'BBU1', 'user', NULL, 'default.png'),
(2, 'bbu2', '918e2d91dedb4c9bb0533137ec01fa89', 'BBU2', 'user', NULL, 'default.png'),
(3, 'bbu3', '202cb962ac59075b964b07152d234b70', 'BBU 3', 'user', NULL, 'default.png'),
(4, 'bbu4', '202cb962ac59075b964b07152d234b70', 'BBU 4', 'user', NULL, 'default.png'),
(5, 'bbu5', '202cb962ac59075b964b07152d234b70', 'BBU 5', 'user', NULL, 'default.png'),
(6, 'vitheity', '202cb962ac59075b964b07152d234b70', 'vitheie', 'user', 'yong.vithei@gmail.com', 'default.png'),
(7, 'kaizer', '202cb962ac59075b964b07152d234b70', 'kaizer', 'user', NULL, 'default.png'),
(8, 'vithei4', 'a471faa3540128b0e0e0f5c1cc4fd1d2', 'vithei321', 'user', 'yong.vithei@gmail.com', 'default.png'),
(9, 'vith321', '202cb962ac59075b964b07152d234b70', 'vithwe', 'user', 'yong.vithei@gmail.com', 'default.png'),
(10, 'vithei321', '202cb962ac59075b964b07152d234b70', 'vithei321', 'user', 'yong.vithei@gmail.com1', 'vithei321.jpg'),
(11, 'vithei122', '202cb962ac59075b964b07152d234b70', 'vithei', 'user', 'null', 'vithei122.jpg'),
(12, 'vithei', '202cb962ac59075b964b07152d234b70', 'vitheiyong', 'user', NULL, 'default.png'),
(13, 'vith', '25d55ad283aa400af464c76d713c07ad', 'vith', 'user', 'yong.vithei@gmail.com', 'default.png');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `tblcategories`
--
ALTER TABLE `tblcategories`
  ADD PRIMARY KEY (`CategoryID`);

--
-- Indexes for table `tblcontact`
--
ALTER TABLE `tblcontact`
  ADD PRIMARY KEY (`contactID`);

--
-- Indexes for table `tblproducts`
--
ALTER TABLE `tblproducts`
  ADD PRIMARY KEY (`ProductID`);

--
-- Indexes for table `tbluser`
--
ALTER TABLE `tbluser`
  ADD PRIMARY KEY (`UserID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `tblcategories`
--
ALTER TABLE `tblcategories`
  MODIFY `CategoryID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `tblcontact`
--
ALTER TABLE `tblcontact`
  MODIFY `contactID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=36;

--
-- AUTO_INCREMENT for table `tblproducts`
--
ALTER TABLE `tblproducts`
  MODIFY `ProductID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `tbluser`
--
ALTER TABLE `tbluser`
  MODIFY `UserID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
