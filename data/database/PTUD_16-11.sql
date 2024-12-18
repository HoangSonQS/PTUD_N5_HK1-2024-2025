USE [master]
GO
/****** Object:  Database [PTUD]    Script Date: 16/11/2024 21:27:06 ******/
CREATE DATABASE [PTUD]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'PTUD', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL16.CSDL\MSSQL\DATA\PTUD' , SIZE = 8192KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
 LOG ON 
( NAME = N'PTUD_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL16.CSDL\MSSQL\DATA\PTUD_log.ldf' , SIZE = 8192KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
 WITH CATALOG_COLLATION = DATABASE_DEFAULT, LEDGER = OFF
GO
ALTER DATABASE [PTUD] SET COMPATIBILITY_LEVEL = 160
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [PTUD].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [PTUD] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [PTUD] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [PTUD] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [PTUD] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [PTUD] SET ARITHABORT OFF 
GO
ALTER DATABASE [PTUD] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [PTUD] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [PTUD] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [PTUD] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [PTUD] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [PTUD] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [PTUD] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [PTUD] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [PTUD] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [PTUD] SET  DISABLE_BROKER 
GO
ALTER DATABASE [PTUD] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [PTUD] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [PTUD] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [PTUD] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [PTUD] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [PTUD] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [PTUD] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [PTUD] SET RECOVERY FULL 
GO
ALTER DATABASE [PTUD] SET  MULTI_USER 
GO
ALTER DATABASE [PTUD] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [PTUD] SET DB_CHAINING OFF 
GO
ALTER DATABASE [PTUD] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [PTUD] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO
ALTER DATABASE [PTUD] SET DELAYED_DURABILITY = DISABLED 
GO
ALTER DATABASE [PTUD] SET ACCELERATED_DATABASE_RECOVERY = OFF  
GO
EXEC sys.sp_db_vardecimal_storage_format N'PTUD', N'ON'
GO
ALTER DATABASE [PTUD] SET QUERY_STORE = ON
GO
ALTER DATABASE [PTUD] SET QUERY_STORE (OPERATION_MODE = READ_WRITE, CLEANUP_POLICY = (STALE_QUERY_THRESHOLD_DAYS = 30), DATA_FLUSH_INTERVAL_SECONDS = 900, INTERVAL_LENGTH_MINUTES = 60, MAX_STORAGE_SIZE_MB = 1000, QUERY_CAPTURE_MODE = AUTO, SIZE_BASED_CLEANUP_MODE = AUTO, MAX_PLANS_PER_QUERY = 200, WAIT_STATS_CAPTURE_MODE = ON)
GO
USE [PTUD]
GO
/****** Object:  Table [dbo].[ChiTietHD_DichVu]    Script Date: 16/11/2024 21:27:06 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ChiTietHD_DichVu](
	[IDHoaDon] [varchar](10) NOT NULL,
	[IDDichVu] [varchar](5) NOT NULL,
	[SoLuong] [int] NOT NULL,
 CONSTRAINT [PK_ChiTietHD_DichVu] PRIMARY KEY CLUSTERED 
(
	[IDHoaDon] ASC,
	[IDDichVu] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[ChiTietHD_Phong]    Script Date: 16/11/2024 21:27:07 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ChiTietHD_Phong](
	[IDHoaDon] [varchar](10) NOT NULL,
	[IDPhong] [varchar](6) NOT NULL,
	[GioCheckout] [datetime] NOT NULL,
 CONSTRAINT [PK_ChiTietHD_Phong] PRIMARY KEY CLUSTERED 
(
	[IDHoaDon] ASC,
	[IDPhong] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[DichVu]    Script Date: 16/11/2024 21:27:07 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[DichVu](
	[IDDichVu] [varchar](5) NOT NULL,
	[TenSanPham] [nvarchar](50) NOT NULL,
	[SoLuong] [int] NOT NULL,
	[DonGia] [money] NOT NULL,
 CONSTRAINT [PK_DichVu] PRIMARY KEY CLUSTERED 
(
	[IDDichVu] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[HoaDon]    Script Date: 16/11/2024 21:27:07 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[HoaDon](
	[IDHoaDon] [varchar](10) NOT NULL,
	[IDNhanVien] [varchar](10) NOT NULL,
	[IDKhachHang] [varchar](10) NOT NULL,
	[IDKhuyenMai] [varchar](8) NULL,
	[ThoiGianTao] [datetime] NOT NULL,
	[ThoiGianCheckin] [datetime] NOT NULL,
 CONSTRAINT [PK_HoaDon] PRIMARY KEY CLUSTERED 
(
	[IDHoaDon] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[KhachHang]    Script Date: 16/11/2024 21:27:07 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[KhachHang](
	[IDKhachHang] [varchar](10) NOT NULL,
	[TenKhachHang] [nvarchar](50) NOT NULL,
	[SoDienThoai] [varchar](10) NOT NULL,
	[NgaySinh] [date] NOT NULL,
	[TichDiem] [int] NULL,
	[CCCD] [varchar](12) NOT NULL,
 CONSTRAINT [PK_KhachHang] PRIMARY KEY CLUSTERED 
(
	[IDKhachHang] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[KhuyenMai]    Script Date: 16/11/2024 21:27:07 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[KhuyenMai](
	[IDKhuyenMai] [varchar](8) NOT NULL,
	[TenKhuyenMai] [nvarchar](50) NOT NULL,
	[ChietKhau] [float] NOT NULL,
 CONSTRAINT [PK_KhuyenMai] PRIMARY KEY CLUSTERED 
(
	[IDKhuyenMai] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[NhanVien]    Script Date: 16/11/2024 21:27:07 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[NhanVien](
	[IDNhanVien] [varchar](10) NOT NULL,
	[TenNhanVien] [nvarchar](50) NOT NULL,
	[SoDienThoai] [varchar](10) NOT NULL,
	[NgaySinh] [date] NOT NULL,
	[GioiTinh] [bit] NULL,
	[CCCD] [varchar](12) NOT NULL,
	[ChucVu] [int] NOT NULL,
 CONSTRAINT [PK_NhanVien] PRIMARY KEY CLUSTERED 
(
	[IDNhanVien] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[PhieuThuePhong]    Script Date: 16/11/2024 21:27:07 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[PhieuThuePhong](
	[IDPhieuThue] [varchar](11) NOT NULL,
	[IDKhachHang] [varchar](10) NOT NULL,
	[IDPhong] [varchar](6) NOT NULL,
	[IDNhanVien] [varchar](10) NOT NULL,
	[ThoiGianNhanPhong] [datetime] NOT NULL,
	[ThoiHanGiaoPhong] [datetime] NOT NULL,
	[HieuLuc] [bit] NULL,
 CONSTRAINT [PK_PhieuThuePhong] PRIMARY KEY CLUSTERED 
(
	[IDPhieuThue] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Phong]    Script Date: 16/11/2024 21:27:07 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Phong](
	[IDPhong] [varchar](6) NOT NULL,
	[LoaiPhong] [int] NOT NULL,
	[DonGia] [money] NOT NULL,
	[TrangThai] [int] NOT NULL,
	[TieuChi] [nvarchar](50) NULL,
 CONSTRAINT [PK_Phong] PRIMARY KEY CLUSTERED 
(
	[IDPhong] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[TaiKhoan]    Script Date: 16/11/2024 21:27:07 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[TaiKhoan](
	[IDTaiKhoan] [varchar](6) NOT NULL,
	[MatKhau] [varchar](50) NOT NULL,
	[TrangThai] [nvarchar](50) NULL,
	[IDNhanVien] [varchar](10) NOT NULL,
 CONSTRAINT [PK_TaiKhoan_1] PRIMARY KEY CLUSTERED 
(
	[IDTaiKhoan] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
INSERT [dbo].[ChiTietHD_DichVu] ([IDHoaDon], [IDDichVu], [SoLuong]) VALUES (N'HD24100301', N'SP001', 2)
GO
INSERT [dbo].[ChiTietHD_Phong] ([IDHoaDon], [IDPhong], [GioCheckout]) VALUES (N'HD24100301', N'T01P02', CAST(N'2024-10-03T12:00:00.000' AS DateTime))
GO
INSERT [dbo].[DichVu] ([IDDichVu], [TenSanPham], [SoLuong], [DonGia]) VALUES (N'SP001', N'Nước suối', 240, 10000.0000)
GO
INSERT [dbo].[HoaDon] ([IDHoaDon], [IDNhanVien], [IDKhachHang], [IDKhuyenMai], [ThoiGianTao], [ThoiGianCheckin]) VALUES (N'HD24100301', N'NV24100301', N'KH24100301', N'KM241001', CAST(N'2024-10-03T12:00:00.000' AS DateTime), CAST(N'2024-10-03T12:00:00.000' AS DateTime))
GO
INSERT [dbo].[KhachHang] ([IDKhachHang], [TenKhachHang], [SoDienThoai], [NgaySinh], [TichDiem], [CCCD]) VALUES (N'KH24100301', N'Nguyễn Hoàng Sơn', N'0123456789', CAST(N'2004-10-03' AS Date), 0, N'087200001111')
INSERT [dbo].[KhachHang] ([IDKhachHang], [TenKhachHang], [SoDienThoai], [NgaySinh], [TichDiem], [CCCD]) VALUES (N'KH24100302', N'Huỳnh Công Ý', N'0766923478', CAST(N'2004-04-13' AS Date), 0, N'087200002222')
INSERT [dbo].[KhachHang] ([IDKhachHang], [TenKhachHang], [SoDienThoai], [NgaySinh], [TichDiem], [CCCD]) VALUES (N'KH24100303', N'Nguyễn Phú Triệu', N'0385412905', CAST(N'2004-12-08' AS Date), 0, N'087200003333')
INSERT [dbo].[KhachHang] ([IDKhachHang], [TenKhachHang], [SoDienThoai], [NgaySinh], [TichDiem], [CCCD]) VALUES (N'KH24100304', N'Nguyễn Văn Khải Tiến', N'0812345678', CAST(N'2004-09-26' AS Date), 0, N'087200004444')
INSERT [dbo].[KhachHang] ([IDKhachHang], [TenKhachHang], [SoDienThoai], [NgaySinh], [TichDiem], [CCCD]) VALUES (N'NV24102301', N'Nguyễn Hải Đăng', N'0362553366', CAST(N'2000-10-01' AS Date), 0, N'087204014444')
INSERT [dbo].[KhachHang] ([IDKhachHang], [TenKhachHang], [SoDienThoai], [NgaySinh], [TichDiem], [CCCD]) VALUES (N'NV24102401', N'Nguyễn Phú Tri', N'0385412906', CAST(N'2004-12-08' AS Date), 0, N'087200003325')
GO
INSERT [dbo].[KhuyenMai] ([IDKhuyenMai], [TenKhuyenMai], [ChietKhau]) VALUES (N'KM241001', N'Khuyến mãi 1', 1)
GO
INSERT [dbo].[NhanVien] ([IDNhanVien], [TenNhanVien], [SoDienThoai], [NgaySinh], [GioiTinh], [CCCD], [ChucVu]) VALUES (N'NV24100301', N'Lê Nguyễn Phi Trường', N'0701234567', CAST(N'2024-12-05' AS Date), 1, N'087300001111', 1)
INSERT [dbo].[NhanVien] ([IDNhanVien], [TenNhanVien], [SoDienThoai], [NgaySinh], [GioiTinh], [CCCD], [ChucVu]) VALUES (N'NV24100302', N'Trần Thị Anh Thi', N'0801234765', CAST(N'1990-05-05' AS Date), 0, N'087400002222', 2)
INSERT [dbo].[NhanVien] ([IDNhanVien], [TenNhanVien], [SoDienThoai], [NgaySinh], [GioiTinh], [CCCD], [ChucVu]) VALUES (N'NV24102401', N'Huỳnh Công Ý', N'0766923478', CAST(N'2004-04-13' AS Date), 1, N'012345678955', 1)
GO
INSERT [dbo].[PhieuThuePhong] ([IDPhieuThue], [IDKhachHang], [IDPhong], [IDNhanVien], [ThoiGianNhanPhong], [ThoiHanGiaoPhong], [HieuLuc]) VALUES (N'PT241003001', N'KH24100301', N'T01P02', N'NV24100301', CAST(N'2024-10-03T12:00:00.000' AS DateTime), CAST(N'2024-10-04T12:00:00.000' AS DateTime), NULL)
GO
INSERT [dbo].[Phong] ([IDPhong], [LoaiPhong], [DonGia], [TrangThai], [TieuChi]) VALUES (N'T01P01', 1, 200000.0000, 1, NULL)
INSERT [dbo].[Phong] ([IDPhong], [LoaiPhong], [DonGia], [TrangThai], [TieuChi]) VALUES (N'T01P02', 1, 200000.0000, 1, NULL)
INSERT [dbo].[Phong] ([IDPhong], [LoaiPhong], [DonGia], [TrangThai], [TieuChi]) VALUES (N'T01P03', 1, 200000.0000, 2, NULL)
INSERT [dbo].[Phong] ([IDPhong], [LoaiPhong], [DonGia], [TrangThai], [TieuChi]) VALUES (N'T01P04', 1, 200000.0000, 4, NULL)
INSERT [dbo].[Phong] ([IDPhong], [LoaiPhong], [DonGia], [TrangThai], [TieuChi]) VALUES (N'T01P05', 2, 400000.0000, 2, NULL)
INSERT [dbo].[Phong] ([IDPhong], [LoaiPhong], [DonGia], [TrangThai], [TieuChi]) VALUES (N'T01P06', 2, 400000.0000, 1, NULL)
INSERT [dbo].[Phong] ([IDPhong], [LoaiPhong], [DonGia], [TrangThai], [TieuChi]) VALUES (N'T01P07', 2, 400000.0000, 2, NULL)
INSERT [dbo].[Phong] ([IDPhong], [LoaiPhong], [DonGia], [TrangThai], [TieuChi]) VALUES (N'T01P08', 2, 400000.0000, 4, NULL)
INSERT [dbo].[Phong] ([IDPhong], [LoaiPhong], [DonGia], [TrangThai], [TieuChi]) VALUES (N'T01P09', 3, 600000.0000, 2, NULL)
INSERT [dbo].[Phong] ([IDPhong], [LoaiPhong], [DonGia], [TrangThai], [TieuChi]) VALUES (N'T01P10', 3, 600000.0000, 1, NULL)
INSERT [dbo].[Phong] ([IDPhong], [LoaiPhong], [DonGia], [TrangThai], [TieuChi]) VALUES (N'T02P01', 3, 600000.0000, 2, NULL)
INSERT [dbo].[Phong] ([IDPhong], [LoaiPhong], [DonGia], [TrangThai], [TieuChi]) VALUES (N'T02P02', 3, 600000.0000, 1, NULL)
INSERT [dbo].[Phong] ([IDPhong], [LoaiPhong], [DonGia], [TrangThai], [TieuChi]) VALUES (N'T02P03', 1, 200000.0000, 2, NULL)
INSERT [dbo].[Phong] ([IDPhong], [LoaiPhong], [DonGia], [TrangThai], [TieuChi]) VALUES (N'T02P04', 1, 200000.0000, 3, NULL)
INSERT [dbo].[Phong] ([IDPhong], [LoaiPhong], [DonGia], [TrangThai], [TieuChi]) VALUES (N'T02P05', 1, 200000.0000, 1, NULL)
INSERT [dbo].[Phong] ([IDPhong], [LoaiPhong], [DonGia], [TrangThai], [TieuChi]) VALUES (N'T02P06', 1, 200000.0000, 4, NULL)
INSERT [dbo].[Phong] ([IDPhong], [LoaiPhong], [DonGia], [TrangThai], [TieuChi]) VALUES (N'T02P07', 2, 400000.0000, 1, NULL)
INSERT [dbo].[Phong] ([IDPhong], [LoaiPhong], [DonGia], [TrangThai], [TieuChi]) VALUES (N'T02P08', 2, 400000.0000, 2, NULL)
INSERT [dbo].[Phong] ([IDPhong], [LoaiPhong], [DonGia], [TrangThai], [TieuChi]) VALUES (N'T02P09', 2, 400000.0000, 1, NULL)
INSERT [dbo].[Phong] ([IDPhong], [LoaiPhong], [DonGia], [TrangThai], [TieuChi]) VALUES (N'T02P10', 2, 400000.0000, 2, NULL)
INSERT [dbo].[Phong] ([IDPhong], [LoaiPhong], [DonGia], [TrangThai], [TieuChi]) VALUES (N'T03P01', 1, 200000.0000, 1, NULL)
INSERT [dbo].[Phong] ([IDPhong], [LoaiPhong], [DonGia], [TrangThai], [TieuChi]) VALUES (N'T03P02', 1, 200000.0000, 2, NULL)
INSERT [dbo].[Phong] ([IDPhong], [LoaiPhong], [DonGia], [TrangThai], [TieuChi]) VALUES (N'T03P03', 1, 200000.0000, 2, NULL)
INSERT [dbo].[Phong] ([IDPhong], [LoaiPhong], [DonGia], [TrangThai], [TieuChi]) VALUES (N'T03P04', 1, 200000.0000, 4, NULL)
INSERT [dbo].[Phong] ([IDPhong], [LoaiPhong], [DonGia], [TrangThai], [TieuChi]) VALUES (N'T03P05', 2, 400000.0000, 3, NULL)
INSERT [dbo].[Phong] ([IDPhong], [LoaiPhong], [DonGia], [TrangThai], [TieuChi]) VALUES (N'T03P06', 2, 400000.0000, 1, NULL)
INSERT [dbo].[Phong] ([IDPhong], [LoaiPhong], [DonGia], [TrangThai], [TieuChi]) VALUES (N'T03P07', 2, 400000.0000, 2, NULL)
INSERT [dbo].[Phong] ([IDPhong], [LoaiPhong], [DonGia], [TrangThai], [TieuChi]) VALUES (N'T03P08', 2, 400000.0000, 1, NULL)
INSERT [dbo].[Phong] ([IDPhong], [LoaiPhong], [DonGia], [TrangThai], [TieuChi]) VALUES (N'T03P09', 3, 400000.0000, 2, NULL)
INSERT [dbo].[Phong] ([IDPhong], [LoaiPhong], [DonGia], [TrangThai], [TieuChi]) VALUES (N'T03P10', 3, 400000.0000, 1, NULL)
INSERT [dbo].[Phong] ([IDPhong], [LoaiPhong], [DonGia], [TrangThai], [TieuChi]) VALUES (N'T04P01', 3, 600000.0000, 2, NULL)
INSERT [dbo].[Phong] ([IDPhong], [LoaiPhong], [DonGia], [TrangThai], [TieuChi]) VALUES (N'T04P02', 3, 600000.0000, 2, NULL)
INSERT [dbo].[Phong] ([IDPhong], [LoaiPhong], [DonGia], [TrangThai], [TieuChi]) VALUES (N'T04P03', 1, 200000.0000, 3, NULL)
INSERT [dbo].[Phong] ([IDPhong], [LoaiPhong], [DonGia], [TrangThai], [TieuChi]) VALUES (N'T04P04', 1, 200000.0000, 1, NULL)
INSERT [dbo].[Phong] ([IDPhong], [LoaiPhong], [DonGia], [TrangThai], [TieuChi]) VALUES (N'T04P05', 1, 200000.0000, 2, NULL)
INSERT [dbo].[Phong] ([IDPhong], [LoaiPhong], [DonGia], [TrangThai], [TieuChi]) VALUES (N'T04P06', 1, 200000.0000, 4, NULL)
INSERT [dbo].[Phong] ([IDPhong], [LoaiPhong], [DonGia], [TrangThai], [TieuChi]) VALUES (N'T04P07', 2, 400000.0000, 3, NULL)
INSERT [dbo].[Phong] ([IDPhong], [LoaiPhong], [DonGia], [TrangThai], [TieuChi]) VALUES (N'T04P08', 2, 400000.0000, 3, NULL)
INSERT [dbo].[Phong] ([IDPhong], [LoaiPhong], [DonGia], [TrangThai], [TieuChi]) VALUES (N'T04P09', 2, 400000.0000, 1, NULL)
INSERT [dbo].[Phong] ([IDPhong], [LoaiPhong], [DonGia], [TrangThai], [TieuChi]) VALUES (N'T04P10', 2, 400000.0000, 1, NULL)
INSERT [dbo].[Phong] ([IDPhong], [LoaiPhong], [DonGia], [TrangThai], [TieuChi]) VALUES (N'T05P01', 1, 200000.0000, 1, NULL)
INSERT [dbo].[Phong] ([IDPhong], [LoaiPhong], [DonGia], [TrangThai], [TieuChi]) VALUES (N'T05P02', 1, 200000.0000, 2, NULL)
INSERT [dbo].[Phong] ([IDPhong], [LoaiPhong], [DonGia], [TrangThai], [TieuChi]) VALUES (N'T05P03', 1, 200000.0000, 2, NULL)
INSERT [dbo].[Phong] ([IDPhong], [LoaiPhong], [DonGia], [TrangThai], [TieuChi]) VALUES (N'T05P04', 1, 200000.0000, 1, NULL)
INSERT [dbo].[Phong] ([IDPhong], [LoaiPhong], [DonGia], [TrangThai], [TieuChi]) VALUES (N'T05P05', 2, 400000.0000, 2, NULL)
INSERT [dbo].[Phong] ([IDPhong], [LoaiPhong], [DonGia], [TrangThai], [TieuChi]) VALUES (N'T05P06', 2, 400000.0000, 1, NULL)
INSERT [dbo].[Phong] ([IDPhong], [LoaiPhong], [DonGia], [TrangThai], [TieuChi]) VALUES (N'T05P07', 2, 400000.0000, 3, NULL)
INSERT [dbo].[Phong] ([IDPhong], [LoaiPhong], [DonGia], [TrangThai], [TieuChi]) VALUES (N'T05P08', 2, 400000.0000, 2, NULL)
INSERT [dbo].[Phong] ([IDPhong], [LoaiPhong], [DonGia], [TrangThai], [TieuChi]) VALUES (N'T05P09', 3, 600000.0000, 1, NULL)
INSERT [dbo].[Phong] ([IDPhong], [LoaiPhong], [DonGia], [TrangThai], [TieuChi]) VALUES (N'T05P10', 3, 600000.0000, 1, NULL)
GO
INSERT [dbo].[TaiKhoan] ([IDTaiKhoan], [MatKhau], [TrangThai], [IDNhanVien]) VALUES (N'NV0301', N'123456789', N'Đang đăng nhập', N'NV24100301')
INSERT [dbo].[TaiKhoan] ([IDTaiKhoan], [MatKhau], [TrangThai], [IDNhanVien]) VALUES (N'NV0302', N'123456789', N'NULL', N'NV24100302')
GO
ALTER TABLE [dbo].[ChiTietHD_DichVu]  WITH CHECK ADD  CONSTRAINT [FK_ChiTietHD_DichVu_DichVu] FOREIGN KEY([IDDichVu])
REFERENCES [dbo].[DichVu] ([IDDichVu])
GO
ALTER TABLE [dbo].[ChiTietHD_DichVu] CHECK CONSTRAINT [FK_ChiTietHD_DichVu_DichVu]
GO
ALTER TABLE [dbo].[ChiTietHD_DichVu]  WITH CHECK ADD  CONSTRAINT [FK_ChiTietHD_DichVu_HoaDon] FOREIGN KEY([IDHoaDon])
REFERENCES [dbo].[HoaDon] ([IDHoaDon])
GO
ALTER TABLE [dbo].[ChiTietHD_DichVu] CHECK CONSTRAINT [FK_ChiTietHD_DichVu_HoaDon]
GO
ALTER TABLE [dbo].[ChiTietHD_Phong]  WITH CHECK ADD  CONSTRAINT [FK_ChiTietHD_Phong_HoaDon] FOREIGN KEY([IDHoaDon])
REFERENCES [dbo].[HoaDon] ([IDHoaDon])
GO
ALTER TABLE [dbo].[ChiTietHD_Phong] CHECK CONSTRAINT [FK_ChiTietHD_Phong_HoaDon]
GO
ALTER TABLE [dbo].[ChiTietHD_Phong]  WITH CHECK ADD  CONSTRAINT [FK_ChiTietHD_Phong_Phong] FOREIGN KEY([IDPhong])
REFERENCES [dbo].[Phong] ([IDPhong])
GO
ALTER TABLE [dbo].[ChiTietHD_Phong] CHECK CONSTRAINT [FK_ChiTietHD_Phong_Phong]
GO
ALTER TABLE [dbo].[HoaDon]  WITH CHECK ADD  CONSTRAINT [FK_HoaDon_KhachHang] FOREIGN KEY([IDKhachHang])
REFERENCES [dbo].[KhachHang] ([IDKhachHang])
GO
ALTER TABLE [dbo].[HoaDon] CHECK CONSTRAINT [FK_HoaDon_KhachHang]
GO
ALTER TABLE [dbo].[HoaDon]  WITH CHECK ADD  CONSTRAINT [FK_HoaDon_KhuyenMai] FOREIGN KEY([IDKhuyenMai])
REFERENCES [dbo].[KhuyenMai] ([IDKhuyenMai])
GO
ALTER TABLE [dbo].[HoaDon] CHECK CONSTRAINT [FK_HoaDon_KhuyenMai]
GO
ALTER TABLE [dbo].[HoaDon]  WITH CHECK ADD  CONSTRAINT [FK_HoaDon_NhanVien] FOREIGN KEY([IDNhanVien])
REFERENCES [dbo].[NhanVien] ([IDNhanVien])
GO
ALTER TABLE [dbo].[HoaDon] CHECK CONSTRAINT [FK_HoaDon_NhanVien]
GO
ALTER TABLE [dbo].[PhieuThuePhong]  WITH CHECK ADD  CONSTRAINT [FK_PhieuThuePhong_KhachHang] FOREIGN KEY([IDKhachHang])
REFERENCES [dbo].[KhachHang] ([IDKhachHang])
GO
ALTER TABLE [dbo].[PhieuThuePhong] CHECK CONSTRAINT [FK_PhieuThuePhong_KhachHang]
GO
ALTER TABLE [dbo].[PhieuThuePhong]  WITH CHECK ADD  CONSTRAINT [FK_PhieuThuePhong_NhanVien] FOREIGN KEY([IDNhanVien])
REFERENCES [dbo].[NhanVien] ([IDNhanVien])
GO
ALTER TABLE [dbo].[PhieuThuePhong] CHECK CONSTRAINT [FK_PhieuThuePhong_NhanVien]
GO
ALTER TABLE [dbo].[PhieuThuePhong]  WITH CHECK ADD  CONSTRAINT [FK_PhieuThuePhong_Phong] FOREIGN KEY([IDPhong])
REFERENCES [dbo].[Phong] ([IDPhong])
GO
ALTER TABLE [dbo].[PhieuThuePhong] CHECK CONSTRAINT [FK_PhieuThuePhong_Phong]
GO
ALTER TABLE [dbo].[TaiKhoan]  WITH CHECK ADD  CONSTRAINT [FK_TaiKhoan_NhanVien] FOREIGN KEY([IDNhanVien])
REFERENCES [dbo].[NhanVien] ([IDNhanVien])
GO
ALTER TABLE [dbo].[TaiKhoan] CHECK CONSTRAINT [FK_TaiKhoan_NhanVien]
GO
USE [master]
GO
ALTER DATABASE [PTUD] SET  READ_WRITE 
GO
