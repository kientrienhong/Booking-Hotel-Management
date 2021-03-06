USE [master]
GO
/****** Object:  Database [LAB3]    Script Date: 11/7/2020 5:13:55 PM ******/
CREATE DATABASE [LAB3] ON  PRIMARY 
( NAME = N'LAB3', FILENAME = N'C:\Program Files (x86)\Microsoft SQL Server\MSSQL10.SQLEXPRESS\MSSQL\DATA\LAB3.mdf' , SIZE = 2048KB , MAXSIZE = UNLIMITED, FILEGROWTH = 1024KB )
 LOG ON 
( NAME = N'LAB3_log', FILENAME = N'C:\Program Files (x86)\Microsoft SQL Server\MSSQL10.SQLEXPRESS\MSSQL\DATA\LAB3_log.ldf' , SIZE = 1024KB , MAXSIZE = 2048GB , FILEGROWTH = 10%)
GO
ALTER DATABASE [LAB3] SET COMPATIBILITY_LEVEL = 100
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [LAB3].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [LAB3] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [LAB3] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [LAB3] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [LAB3] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [LAB3] SET ARITHABORT OFF 
GO
ALTER DATABASE [LAB3] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [LAB3] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [LAB3] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [LAB3] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [LAB3] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [LAB3] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [LAB3] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [LAB3] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [LAB3] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [LAB3] SET  DISABLE_BROKER 
GO
ALTER DATABASE [LAB3] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [LAB3] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [LAB3] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [LAB3] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [LAB3] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [LAB3] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [LAB3] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [LAB3] SET RECOVERY SIMPLE 
GO
ALTER DATABASE [LAB3] SET  MULTI_USER 
GO
ALTER DATABASE [LAB3] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [LAB3] SET DB_CHAINING OFF 
GO
USE [LAB3]
GO
/****** Object:  Table [dbo].[tblFeedback]    Script Date: 11/7/2020 5:13:55 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tblFeedback](
	[registrationId] [varchar](50) NOT NULL,
	[hotelId] [int] NOT NULL,
	[idRoomType] [varchar](20) NOT NULL,
	[status] [varchar](15) NOT NULL,
	[rating] [int] NOT NULL,
 CONSTRAINT [PK_tblFeedback] PRIMARY KEY CLUSTERED 
(
	[registrationId] ASC,
	[hotelId] ASC,
	[idRoomType] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tblHotel]    Script Date: 11/7/2020 5:13:55 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tblHotel](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[name] [varchar](50) NOT NULL,
	[area] [varchar](50) NOT NULL,
	[status] [varchar](15) NOT NULL,
	[totalRoom] [int] NOT NULL,
 CONSTRAINT [PK_tblHotel] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tblInvoice]    Script Date: 11/7/2020 5:13:55 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tblInvoice](
	[id] [varchar](50) NOT NULL,
	[registrationId] [varchar](50) NOT NULL,
	[checkInDate] [datetime] NOT NULL,
	[checkOutDate] [datetime] NOT NULL,
	[totalPrice] [float] NOT NULL,
	[status] [varchar](15) NOT NULL,
	[bookingDate] [datetime] NOT NULL,
	[phone] [varchar](10) NOT NULL,
	[recipientName] [nvarchar](50) NOT NULL,
	[isConfirm] [bit] NOT NULL,
 CONSTRAINT [PK_Invoice] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tblInvoiceDetail]    Script Date: 11/7/2020 5:13:55 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tblInvoiceDetail](
	[invoiceId] [varchar](50) NOT NULL,
	[idRoomType] [varchar](20) NOT NULL,
	[quantity] [int] NOT NULL,
	[idHotel] [int] NOT NULL,
	[price] [float] NOT NULL,
	[status] [varchar](15) NOT NULL,
 CONSTRAINT [PK_InvoiceDetail] PRIMARY KEY CLUSTERED 
(
	[invoiceId] ASC,
	[idRoomType] ASC,
	[idHotel] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tblRegistration]    Script Date: 11/7/2020 5:13:55 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tblRegistration](
	[id] [varchar](50) NOT NULL,
	[password] [varchar](80) NOT NULL,
	[name] [nvarchar](50) NOT NULL,
	[roleId] [varchar](15) NOT NULL,
	[status] [varchar](15) NOT NULL,
	[phone] [varchar](10) NOT NULL,
	[address] [varchar](30) NOT NULL,
	[createdDate] [date] NOT NULL,
 CONSTRAINT [PK_tblRegistration] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tblRole]    Script Date: 11/7/2020 5:13:55 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tblRole](
	[id] [varchar](15) NOT NULL,
	[name] [varchar](50) NOT NULL,
 CONSTRAINT [PK_tblRole] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tblRoom]    Script Date: 11/7/2020 5:13:55 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tblRoom](
	[idHotel] [int] NOT NULL,
	[idRoomType] [varchar](20) NOT NULL,
	[totalAmount] [int] NOT NULL,
	[status] [varchar](15) NOT NULL,
	[price] [float] NOT NULL,
 CONSTRAINT [PK_tblRoom] PRIMARY KEY CLUSTERED 
(
	[idHotel] ASC,
	[idRoomType] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tblRoomType]    Script Date: 11/7/2020 5:13:55 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tblRoomType](
	[id] [varchar](20) NOT NULL,
	[name] [varchar](50) NOT NULL,
 CONSTRAINT [PK_tblRoomType] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
INSERT [dbo].[tblFeedback] ([registrationId], [hotelId], [idRoomType], [status], [rating]) VALUES (N'kientrienhong@gmail.com', 2, N'VIP', N'Active', 6)
INSERT [dbo].[tblFeedback] ([registrationId], [hotelId], [idRoomType], [status], [rating]) VALUES (N'test1@gmail.com', 1, N'DB', N'Active', 6)
INSERT [dbo].[tblFeedback] ([registrationId], [hotelId], [idRoomType], [status], [rating]) VALUES (N'test1@gmail.com', 1, N'SG', N'Active', 10)
SET IDENTITY_INSERT [dbo].[tblHotel] ON 

INSERT [dbo].[tblHotel] ([id], [name], [area], [status], [totalRoom]) VALUES (1, N'Deep Sea', N'Quan 10', N'Active', 13)
INSERT [dbo].[tblHotel] ([id], [name], [area], [status], [totalRoom]) VALUES (2, N'Blue Sea', N'Quan 6', N'Active', 15)
SET IDENTITY_INSERT [dbo].[tblHotel] OFF
INSERT [dbo].[tblInvoice] ([id], [registrationId], [checkInDate], [checkOutDate], [totalPrice], [status], [bookingDate], [phone], [recipientName], [isConfirm]) VALUES (N'kientrienhong@gmail.com_DDI_20201107', N'kientrienhong@gmail.com', CAST(N'2020-11-03T00:00:00.000' AS DateTime), CAST(N'2020-11-06T00:00:00.000' AS DateTime), 30, N'Deative', CAST(N'2020-11-07T00:00:00.000' AS DateTime), N'5678493021', N'Trien', 1)
INSERT [dbo].[tblInvoice] ([id], [registrationId], [checkInDate], [checkOutDate], [totalPrice], [status], [bookingDate], [phone], [recipientName], [isConfirm]) VALUES (N'kientrienhong@gmail.com_MLG_20201107', N'kientrienhong@gmail.com', CAST(N'2020-11-08T00:00:00.000' AS DateTime), CAST(N'2020-11-11T00:00:00.000' AS DateTime), 15, N'Active', CAST(N'2020-11-07T00:00:00.000' AS DateTime), N'5678493021', N'Trien', 1)
INSERT [dbo].[tblInvoice] ([id], [registrationId], [checkInDate], [checkOutDate], [totalPrice], [status], [bookingDate], [phone], [recipientName], [isConfirm]) VALUES (N'kientrienhong@gmail.com_PQS_20201106', N'kientrienhong@gmail.com', CAST(N'2020-11-01T00:00:00.000' AS DateTime), CAST(N'2020-11-30T00:00:00.000' AS DateTime), 35, N'Active', CAST(N'2020-11-06T00:00:00.000' AS DateTime), N'5678493021', N'Trien', 1)
INSERT [dbo].[tblInvoice] ([id], [registrationId], [checkInDate], [checkOutDate], [totalPrice], [status], [bookingDate], [phone], [recipientName], [isConfirm]) VALUES (N'kientrienhong@gmail.com_UNR_20201106', N'kientrienhong@gmail.com', CAST(N'2020-11-01T00:00:00.000' AS DateTime), CAST(N'2020-11-05T00:00:00.000' AS DateTime), 20, N'Deative', CAST(N'2020-11-06T00:00:00.000' AS DateTime), N'5678493021', N'Trien', 1)
INSERT [dbo].[tblInvoice] ([id], [registrationId], [checkInDate], [checkOutDate], [totalPrice], [status], [bookingDate], [phone], [recipientName], [isConfirm]) VALUES (N'kientrienhong@gmail.com_WFE_20201106', N'kientrienhong@gmail.com', CAST(N'2020-11-01T00:00:00.000' AS DateTime), CAST(N'2020-11-30T00:00:00.000' AS DateTime), 25, N'Active', CAST(N'2020-11-06T00:00:00.000' AS DateTime), N'5678493021', N'Trien', 1)
INSERT [dbo].[tblInvoice] ([id], [registrationId], [checkInDate], [checkOutDate], [totalPrice], [status], [bookingDate], [phone], [recipientName], [isConfirm]) VALUES (N'kientrienhong@gmail.com_ZUF_20201106', N'kientrienhong@gmail.com', CAST(N'2020-11-01T00:00:00.000' AS DateTime), CAST(N'2020-11-30T00:00:00.000' AS DateTime), 25, N'Active', CAST(N'2020-11-06T00:00:00.000' AS DateTime), N'5678493021', N'Trien', 1)
INSERT [dbo].[tblInvoice] ([id], [registrationId], [checkInDate], [checkOutDate], [totalPrice], [status], [bookingDate], [phone], [recipientName], [isConfirm]) VALUES (N'test1@gmail.com_HT1', N'test1@gmail.com', CAST(N'2020-10-10T00:00:00.000' AS DateTime), CAST(N'2020-11-01T00:00:00.000' AS DateTime), 50, N'Deative', CAST(N'2020-10-10T00:00:00.000' AS DateTime), N'5678493021', N'Test', 1)
INSERT [dbo].[tblInvoice] ([id], [registrationId], [checkInDate], [checkOutDate], [totalPrice], [status], [bookingDate], [phone], [recipientName], [isConfirm]) VALUES (N'test1@gmail.com_HT2', N'test1@gmail.com', CAST(N'2020-01-01T00:00:00.000' AS DateTime), CAST(N'2025-10-10T00:00:00.000' AS DateTime), 50, N'Active', CAST(N'2020-10-11T00:00:00.000' AS DateTime), N'5678493021', N'Test', 1)
INSERT [dbo].[tblInvoice] ([id], [registrationId], [checkInDate], [checkOutDate], [totalPrice], [status], [bookingDate], [phone], [recipientName], [isConfirm]) VALUES (N'test1@gmail.com_HT3', N'test1@gmail.com', CAST(N'2020-01-01T00:00:00.000' AS DateTime), CAST(N'2025-10-10T00:00:00.000' AS DateTime), 50, N'Active', CAST(N'2020-10-12T00:00:00.000' AS DateTime), N'5678493021', N'Test', 1)
INSERT [dbo].[tblInvoiceDetail] ([invoiceId], [idRoomType], [quantity], [idHotel], [price], [status]) VALUES (N'kientrienhong@gmail.com_DDI_20201107', N'VIP', 1, 2, 30, N'Active')
INSERT [dbo].[tblInvoiceDetail] ([invoiceId], [idRoomType], [quantity], [idHotel], [price], [status]) VALUES (N'kientrienhong@gmail.com_MLG_20201107', N'SG', 1, 2, 5, N'Active')
INSERT [dbo].[tblInvoiceDetail] ([invoiceId], [idRoomType], [quantity], [idHotel], [price], [status]) VALUES (N'kientrienhong@gmail.com_PQS_20201106', N'SG', 1, 2, 5, N'Active')
INSERT [dbo].[tblInvoiceDetail] ([invoiceId], [idRoomType], [quantity], [idHotel], [price], [status]) VALUES (N'kientrienhong@gmail.com_PQS_20201106', N'VIP', 1, 2, 30, N'Active')
INSERT [dbo].[tblInvoiceDetail] ([invoiceId], [idRoomType], [quantity], [idHotel], [price], [status]) VALUES (N'kientrienhong@gmail.com_UNR_20201106', N'SG', 1, 1, 10, N'Active')
INSERT [dbo].[tblInvoiceDetail] ([invoiceId], [idRoomType], [quantity], [idHotel], [price], [status]) VALUES (N'kientrienhong@gmail.com_UNR_20201106', N'VIP', 1, 2, 30, N'Active')
INSERT [dbo].[tblInvoiceDetail] ([invoiceId], [idRoomType], [quantity], [idHotel], [price], [status]) VALUES (N'kientrienhong@gmail.com_WFE_20201106', N'DB', 1, 1, 15, N'Active')
INSERT [dbo].[tblInvoiceDetail] ([invoiceId], [idRoomType], [quantity], [idHotel], [price], [status]) VALUES (N'kientrienhong@gmail.com_WFE_20201106', N'SG', 1, 1, 10, N'Active')
INSERT [dbo].[tblInvoiceDetail] ([invoiceId], [idRoomType], [quantity], [idHotel], [price], [status]) VALUES (N'kientrienhong@gmail.com_ZUF_20201106', N'SG', 1, 2, 5, N'Active')
INSERT [dbo].[tblInvoiceDetail] ([invoiceId], [idRoomType], [quantity], [idHotel], [price], [status]) VALUES (N'kientrienhong@gmail.com_ZUF_20201106', N'VIP', 1, 1, 20, N'Active')
INSERT [dbo].[tblInvoiceDetail] ([invoiceId], [idRoomType], [quantity], [idHotel], [price], [status]) VALUES (N'test1@gmail.com_HT1', N'DB', 2, 1, 30, N'Active')
INSERT [dbo].[tblInvoiceDetail] ([invoiceId], [idRoomType], [quantity], [idHotel], [price], [status]) VALUES (N'test1@gmail.com_HT1', N'SG', 2, 1, 20, N'Active')
INSERT [dbo].[tblInvoiceDetail] ([invoiceId], [idRoomType], [quantity], [idHotel], [price], [status]) VALUES (N'test1@gmail.com_HT2', N'DB', 2, 2, 50, N'Active')
INSERT [dbo].[tblInvoiceDetail] ([invoiceId], [idRoomType], [quantity], [idHotel], [price], [status]) VALUES (N'test1@gmail.com_HT3', N'DB', 2, 2, 50, N'Active')
INSERT [dbo].[tblRegistration] ([id], [password], [name], [roleId], [status], [phone], [address], [createdDate]) VALUES (N'admin@gmail.com', N'3ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4', N'ADMIN', N'AD', N'Active', N'1234567890', N's', CAST(N'2020-10-31' AS Date))
INSERT [dbo].[tblRegistration] ([id], [password], [name], [roleId], [status], [phone], [address], [createdDate]) VALUES (N'kientrienhong@gmail.com', N'3ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4', N'Trien', N'MEM', N'Active', N'5678493021', N'Test address', CAST(N'2020-11-05' AS Date))
INSERT [dbo].[tblRegistration] ([id], [password], [name], [roleId], [status], [phone], [address], [createdDate]) VALUES (N'test@gmail.com', N'3ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4', N'Test', N'MEM', N'Active', N'1234567890', N'd', CAST(N'2020-10-31' AS Date))
INSERT [dbo].[tblRegistration] ([id], [password], [name], [roleId], [status], [phone], [address], [createdDate]) VALUES (N'test1@gmail.com', N'3ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4', N'Test', N'MEM', N'Active', N'1234567890', N'c', CAST(N'2020-10-31' AS Date))
INSERT [dbo].[tblRegistration] ([id], [password], [name], [roleId], [status], [phone], [address], [createdDate]) VALUES (N'test19@gmail.com', N'3ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4', N'Test19', N'MEM', N'Active', N'1234567890', N'a', CAST(N'2020-10-31' AS Date))
INSERT [dbo].[tblRegistration] ([id], [password], [name], [roleId], [status], [phone], [address], [createdDate]) VALUES (N'test2@gmail.com', N'3ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4', N'asd', N'MEM', N'Active', N'1234567890', N'x', CAST(N'2020-10-31' AS Date))
INSERT [dbo].[tblRegistration] ([id], [password], [name], [roleId], [status], [phone], [address], [createdDate]) VALUES (N'test6@gmail.com', N'3ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4', N'asdf', N'MEM', N'Active', N'1234567890', N'test', CAST(N'2020-11-02' AS Date))
INSERT [dbo].[tblRegistration] ([id], [password], [name], [roleId], [status], [phone], [address], [createdDate]) VALUES (N'trienhkse140737@fpt.edu.vn', N'3ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4', N'Dave', N'MEM', N'Active', N'5678493021', N'Test address', CAST(N'2020-11-07' AS Date))
INSERT [dbo].[tblRole] ([id], [name]) VALUES (N'AD', N'admin')
INSERT [dbo].[tblRole] ([id], [name]) VALUES (N'MEM', N'member')
INSERT [dbo].[tblRoom] ([idHotel], [idRoomType], [totalAmount], [status], [price]) VALUES (1, N'DB', 4, N'Active', 15)
INSERT [dbo].[tblRoom] ([idHotel], [idRoomType], [totalAmount], [status], [price]) VALUES (1, N'SG', 5, N'Active', 10)
INSERT [dbo].[tblRoom] ([idHotel], [idRoomType], [totalAmount], [status], [price]) VALUES (1, N'VIP', 4, N'Active', 20)
INSERT [dbo].[tblRoom] ([idHotel], [idRoomType], [totalAmount], [status], [price]) VALUES (2, N'DB', 5, N'Active', 25)
INSERT [dbo].[tblRoom] ([idHotel], [idRoomType], [totalAmount], [status], [price]) VALUES (2, N'SG', 5, N'Active', 5)
INSERT [dbo].[tblRoom] ([idHotel], [idRoomType], [totalAmount], [status], [price]) VALUES (2, N'VIP', 5, N'Active', 30)
INSERT [dbo].[tblRoomType] ([id], [name]) VALUES (N'DB', N'double')
INSERT [dbo].[tblRoomType] ([id], [name]) VALUES (N'SG', N'single')
INSERT [dbo].[tblRoomType] ([id], [name]) VALUES (N'VIP', N'vip')
ALTER TABLE [dbo].[tblFeedback]  WITH CHECK ADD  CONSTRAINT [FK_tblFeedback_tblRegistration] FOREIGN KEY([registrationId])
REFERENCES [dbo].[tblRegistration] ([id])
GO
ALTER TABLE [dbo].[tblFeedback] CHECK CONSTRAINT [FK_tblFeedback_tblRegistration]
GO
ALTER TABLE [dbo].[tblFeedback]  WITH CHECK ADD  CONSTRAINT [FK_tblFeedback_tblRoom] FOREIGN KEY([hotelId], [idRoomType])
REFERENCES [dbo].[tblRoom] ([idHotel], [idRoomType])
GO
ALTER TABLE [dbo].[tblFeedback] CHECK CONSTRAINT [FK_tblFeedback_tblRoom]
GO
ALTER TABLE [dbo].[tblInvoice]  WITH CHECK ADD  CONSTRAINT [FK_tblInvoice_tblRegistration] FOREIGN KEY([registrationId])
REFERENCES [dbo].[tblRegistration] ([id])
GO
ALTER TABLE [dbo].[tblInvoice] CHECK CONSTRAINT [FK_tblInvoice_tblRegistration]
GO
ALTER TABLE [dbo].[tblInvoiceDetail]  WITH CHECK ADD  CONSTRAINT [FK_tblInvoiceDetail_tblInvoice] FOREIGN KEY([invoiceId])
REFERENCES [dbo].[tblInvoice] ([id])
GO
ALTER TABLE [dbo].[tblInvoiceDetail] CHECK CONSTRAINT [FK_tblInvoiceDetail_tblInvoice]
GO
ALTER TABLE [dbo].[tblInvoiceDetail]  WITH CHECK ADD  CONSTRAINT [FK_tblInvoiceDetail_tblRoom] FOREIGN KEY([idHotel], [idRoomType])
REFERENCES [dbo].[tblRoom] ([idHotel], [idRoomType])
GO
ALTER TABLE [dbo].[tblInvoiceDetail] CHECK CONSTRAINT [FK_tblInvoiceDetail_tblRoom]
GO
ALTER TABLE [dbo].[tblRegistration]  WITH CHECK ADD  CONSTRAINT [FK_tblRegistration_tblRole] FOREIGN KEY([roleId])
REFERENCES [dbo].[tblRole] ([id])
GO
ALTER TABLE [dbo].[tblRegistration] CHECK CONSTRAINT [FK_tblRegistration_tblRole]
GO
ALTER TABLE [dbo].[tblRoom]  WITH CHECK ADD  CONSTRAINT [FK_tblRoom_tblHotel] FOREIGN KEY([idHotel])
REFERENCES [dbo].[tblHotel] ([id])
GO
ALTER TABLE [dbo].[tblRoom] CHECK CONSTRAINT [FK_tblRoom_tblHotel]
GO
ALTER TABLE [dbo].[tblRoom]  WITH CHECK ADD  CONSTRAINT [FK_tblRoom_tblRoomType] FOREIGN KEY([idRoomType])
REFERENCES [dbo].[tblRoomType] ([id])
GO
ALTER TABLE [dbo].[tblRoom] CHECK CONSTRAINT [FK_tblRoom_tblRoomType]
GO
USE [master]
GO
ALTER DATABASE [LAB3] SET  READ_WRITE 
GO
