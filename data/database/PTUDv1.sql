USE [PTUD]
GO
/****** Object:  Table [dbo].[ChiTietHD_DichVu]    Script Date: 17/10/2024 23:41:38 ******/
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
/****** Object:  Table [dbo].[ChiTietHD_Phong]    Script Date: 17/10/2024 23:41:38 ******/
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
/****** Object:  Table [dbo].[DichVu]    Script Date: 17/10/2024 23:41:38 ******/
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
/****** Object:  Table [dbo].[HoaDon]    Script Date: 17/10/2024 23:41:38 ******/
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
/****** Object:  Table [dbo].[KhachHang]    Script Date: 17/10/2024 23:41:38 ******/
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
/****** Object:  Table [dbo].[KhuyenMai]    Script Date: 17/10/2024 23:41:38 ******/
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
/****** Object:  Table [dbo].[NhanVien]    Script Date: 17/10/2024 23:41:38 ******/
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
/****** Object:  Table [dbo].[PhieuThuePhong]    Script Date: 17/10/2024 23:41:38 ******/
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
 CONSTRAINT [PK_PhieuThuePhong] PRIMARY KEY CLUSTERED 
(
	[IDPhieuThue] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Phong]    Script Date: 17/10/2024 23:41:38 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Phong](
	[IDPhong] [varchar](6) NOT NULL,
	[LoaiPhong] [int] NOT NULL,
	[DonGia] [money] NOT NULL,
	[TrangThai] [int] NOT NULL,
 CONSTRAINT [PK_Phong] PRIMARY KEY CLUSTERED 
(
	[IDPhong] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[TaiKhoan]    Script Date: 17/10/2024 23:41:38 ******/
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
GO
INSERT [dbo].[KhuyenMai] ([IDKhuyenMai], [TenKhuyenMai], [ChietKhau]) VALUES (N'KM241001', N'Khuyến mãi 1', 1)
GO
INSERT [dbo].[NhanVien] ([IDNhanVien], [TenNhanVien], [SoDienThoai], [NgaySinh], [GioiTinh], [CCCD], [ChucVu]) VALUES (N'NV24100301', N'Lê Nguyễn Phi Trường', N'0701234567', CAST(N'2024-12-05' AS Date), 1, N'087300001111', 1)
INSERT [dbo].[NhanVien] ([IDNhanVien], [TenNhanVien], [SoDienThoai], [NgaySinh], [GioiTinh], [CCCD], [ChucVu]) VALUES (N'NV24100302', N'Trần Thị Anh Thi', N'0801234765', CAST(N'1990-05-05' AS Date), 0, N'087400002222', 2)
GO
INSERT [dbo].[PhieuThuePhong] ([IDPhieuThue], [IDKhachHang], [IDPhong], [IDNhanVien], [ThoiGianNhanPhong], [ThoiHanGiaoPhong]) VALUES (N'PT241003001', N'KH24100301', N'T01P02', N'NV24100301', CAST(N'2024-10-03T12:00:00.000' AS DateTime), CAST(N'2024-10-04T12:00:00.000' AS DateTime))
GO
INSERT [dbo].[Phong] ([IDPhong], [LoaiPhong], [DonGia], [TrangThai]) VALUES (N'T01P01', 1, 200000.0000, 1)
INSERT [dbo].[Phong] ([IDPhong], [LoaiPhong], [DonGia], [TrangThai]) VALUES (N'T01P02', 1, 200000.0000, 2)
INSERT [dbo].[Phong] ([IDPhong], [LoaiPhong], [DonGia], [TrangThai]) VALUES (N'T01P03', 2, 400000.0000, 3)
INSERT [dbo].[Phong] ([IDPhong], [LoaiPhong], [DonGia], [TrangThai]) VALUES (N'T01P04', 3, 600000.0000, 4)
GO
INSERT [dbo].[TaiKhoan] ([IDTaiKhoan], [MatKhau], [TrangThai], [IDNhanVien]) VALUES (N'NV0301', N'123456789', N'Đang đăng nhập', N'NV24100301')
INSERT [dbo].[TaiKhoan] ([IDTaiKhoan], [MatKhau], [TrangThai], [IDNhanVien]) VALUES (N'NV0302', N'123456789', NULL, N'NV24100302')
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
