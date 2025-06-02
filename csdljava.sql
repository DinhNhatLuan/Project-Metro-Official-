CREATE TABLE TAIKHOAN (
    TenTK VARCHAR(30) PRIMARY KEY,
    MatKhau VARCHAR(30),
    MaNV INT NOT NULL
);

CREATE TABLE NHANVIEN (
    MaNV INT AUTO_INCREMENT PRIMARY KEY,
    HoTen VARCHAR(50) NOT NULL,
    NgaySinh DATE,
    ChucVu VARCHAR(50),
    DiaChi VARCHAR(50),
    GioiTinh BOOLEAN,
    Email VARCHAR(50),
    SoDT VARCHAR(20),
    Luong INT,
    NgayVaoLam DATE,
    CaLam VARCHAR(20),
    MaNVQL INT
);

CREATE TABLE VE (
    MaVe INT AUTO_INCREMENT PRIMARY KEY,
    Gia INT,
    MaLoaiVe INT NOT NULL,
    ThoiDiemMua DATETIME,
    MaLT INT,
    MaTT INT
);

CREATE TABLE GATAU (
    MaGT INT AUTO_INCREMENT PRIMARY KEY,
    TenGT VARCHAR(50),
    ViTri VARCHAR(50)
);

CREATE TABLE THUTUGA (
    MaTT INT AUTO_INCREMENT PRIMARY KEY,
    MaGaDi INT,
    MaGaDen INT,
    GiaTinhThem INT NOT NULL
);

CREATE TABLE LOAIVE (
    MaLoaiVe INT AUTO_INCREMENT PRIMARY KEY,
    MoTa VARCHAR(100),
    GiaGoc INT
);

CREATE TABLE TUYEN (
	MaTuyen INT AUTO_INCREMENT PRIMARY KEY,
    TenTuyen VARCHAR(50),
    DoDai INT, 
    GiaChenhLech INT
);
CREATE TABLE CT_TUYEN (
    MaTuyen INT,
    MaGT INT,
    ThuTu INT,
    PRIMARY KEY (MaTuyen, MaGT)
);


CREATE TABLE LICHTRINH (
    MaLT INT AUTO_INCREMENT PRIMARY KEY,
    NgayKH DATE,
    GioKH TIME,
    TinhTrang VARCHAR(10),
    MaTau INT,
    MaNVLT INT,
    MaNVLL INT,
    SoLuongVeDB INT DEFAULT 0,
    MaTuyen INT NOT NULL
);

CREATE TABLE LICHDUNG (
    MaLT INT NOT NULL,
    MaGT INT NOT NULL,
    GioDen TIME,
    GioDi TIME,
    ThuTu INT,
    PRIMARY KEY (MaLT, MaGT)
);

CREATE TABLE TAU (
    MaTau INT AUTO_INCREMENT PRIMARY KEY,
    TenTau VARCHAR(30),
    SucChua INT
);


-- alter table TAIKHOAN add constraint tkhoan_pk primary key(TenTK);
-- alter table NHANVIEN add constraint nvien_pk primary key(MaNV);
-- alter table VE add constraint ve_pk primary key(MaVe);
-- alter table GATAU add constraint gtau_pk primary key(MaGT);
-- alter table THUTUGA add constraint ttga_pk primary key(MaTT);
-- alter table LOAIVE add constraint loaive_pk primary key(MaLoaiVe);
-- alter table LICHTRINH add constraint ltrinh_pk primary key(MaLT);
-- alter table LICHDUNG add constraint ldung_pk primary key(MaLT,MaGT);
-- alter table TAU add constraint tau_pk primary key(MaTau);


-- Bảng TAIKHOAN tham chiếu đến NHANVIEN
ALTER TABLE TAIKHOAN
ADD CONSTRAINT fk_taikhoan_manv
FOREIGN KEY (MaNV) REFERENCES NHANVIEN(MaNV);

-- Bảng VE tham chiếu đến LOAIVE
ALTER TABLE VE
ADD CONSTRAINT fk_ve_loaive
FOREIGN KEY (MaLoaiVe) REFERENCES LOAIVE(MaLoaiVe);

-- Bảng VE tham chiếu đến LICHTRINH
ALTER TABLE VE
ADD CONSTRAINT fk_ve_lichttrinh
FOREIGN KEY (MaLT) REFERENCES LICHTRINH(MaLT);

-- Bảng VE tham chiếu đến THUTUGA
ALTER TABLE VE
ADD CONSTRAINT fk_ve_thutuga
FOREIGN KEY (MaTT) REFERENCES THUTUGA(MaTT);

-- Bảng THUTUGA tham chiếu đến GATAU (Ga đi)
ALTER TABLE THUTUGA
ADD CONSTRAINT fk_ttga_gadi
FOREIGN KEY (MaGaDi) REFERENCES GATAU(MaGT);

-- Bảng THUTUGA tham chiếu đến GATAU (Ga đến)
ALTER TABLE THUTUGA
ADD CONSTRAINT fk_ttga_gaden
FOREIGN KEY (MaGaDen) REFERENCES GATAU(MaGT);

-- Bảng LICHTRINH tham chiếu đến TAU
ALTER TABLE LICHTRINH
ADD CONSTRAINT fk_ltrinh_tau
FOREIGN KEY (MaTau) REFERENCES TAU(MaTau);

-- Bảng LICHTRINH tham chiếu đến NHANVIEN
ALTER TABLE LICHTRINH
ADD CONSTRAINT fk_ltrinh_nvLT
FOREIGN KEY (MaNVLT) REFERENCES NHANVIEN(MaNV);

ALTER TABLE LICHTRINH
ADD CONSTRAINT fk_ltrinh_nvLL
FOREIGN KEY (MaNVLL) REFERENCES NHANVIEN(MaNV);

-- Bảng LICHDUNG tham chiếu đến LICHTRINH
ALTER TABLE LICHDUNG
ADD CONSTRAINT fk_ldung_ltrinh
FOREIGN KEY (MaLT) REFERENCES LICHTRINH(MaLT);

-- Bảng LICHDUNG tham chiếu đến GATAU
ALTER TABLE LICHDUNG
ADD CONSTRAINT fk_ldung_gatau
FOREIGN KEY (MaGT) REFERENCES GATAU(MaGT);

-- Bảng NHANVIEN có MaNVQL tham chiếu đến chính NHANVIEN (quản lý)
ALTER TABLE NHANVIEN
ADD CONSTRAINT fk_nv_ql
FOREIGN KEY (MaNVQL) REFERENCES NHANVIEN(MaNV);


-- Bảng CT_TUYEN 
ALTER TABLE CT_TUYEN
ADD CONSTRAINT FK_GATT_TUYEN FOREIGN KEY (MaTuyen)
REFERENCES TUYEN(MaTuyen);

ALTER TABLE CT_TUYEN
ADD CONSTRAINT FK_GATT_GATAU FOREIGN KEY (MaGT)
REFERENCES GATAU(MaGT);



-- Xóa bảng có phụ thuộc khóa ngoại trước
DROP TABLE IF EXISTS LICHDUNG;
DROP TABLE IF EXISTS VE;
DROP TABLE IF EXISTS TAIKHOAN;
DROP TABLE IF EXISTS LICHTRINH;
DROP TABLE IF EXISTS THUTUGA;
DROP TABLE IF EXISTS NHANVIEN;
DROP TABLE IF EXISTS TAU;
DROP TABLE IF EXISTS LOAIVE;
DROP TABLE IF EXISTS CT_TUYEN;
DROP TABLE IF EXISTS GATAU;
DROP TABLE IF EXISTS TUYEN;


-- Trigger
DELIMITER $$

CREATE TRIGGER trg_update_ve_insert
AFTER INSERT ON VE
FOR EACH ROW
BEGIN
  UPDATE LICHTRINH
  SET SoLuongVeDB = (
    SELECT COUNT(*) FROM VE WHERE MaLT = NEW.MaLT
  )
  WHERE MaLT = NEW.MaLT;
END$$

DELIMITER ;

DELIMITER $$

CREATE TRIGGER trg_update_ve_delete
AFTER DELETE ON VE
FOR EACH ROW
BEGIN
  UPDATE LICHTRINH
  SET SoLuongVeDB = (
    SELECT COUNT(*) FROM VE WHERE MaLT = OLD.MaLT
  )
  WHERE MaLT = OLD.MaLT;
END$$

DELIMITER ;

-- chạy trigger ở dưới tại root user

DELIMITER $$

CREATE TRIGGER trg_update_ve_update
AFTER UPDATE ON VE
FOR EACH ROW
BEGIN
  -- Nếu MaLT thay đổi, cần cập nhật cả lịch trình cũ và mới
  IF OLD.MaLT != NEW.MaLT THEN
    -- Cập nhật lịch trình cũ
    UPDATE LICHTRINH
    SET SoLuongVeDB = (
      SELECT COUNT(*) FROM VE WHERE MaLT = OLD.MaLT
    )
    WHERE MaLT = OLD.MaLT;

    -- Cập nhật lịch trình mới
    UPDATE LICHTRINH
    SET SoLuongVeDB = (
      SELECT COUNT(*) FROM VE WHERE MaLT = NEW.MaLT
    )
    WHERE MaLT = NEW.MaLT;

  ELSE
    -- Nếu không đổi MaLT, chỉ cần cập nhật một lần
    UPDATE LICHTRINH
    SET SoLuongVeDB = (
      SELECT COUNT(*) FROM VE WHERE MaLT = NEW.MaLT
    )
    WHERE MaLT = NEW.MaLT;
  END IF;
END$$

DELIMITER ;

-- Nhap du lieu
-- NHANVIEN
INSERT INTO NHANVIEN (MaNV, HoTen, NgaySinh, ChucVu, DiaChi, GioiTinh, Email, SoDT, Luong, NgayVaoLam, CaLam, MaNVQL) VALUES
(1, 'Nguyen Van A', '1985-05-10', 'Truong ga', 'Ha Noi', TRUE, 'a@example.com', '0901234567', 15000000, '2020-01-01', 'Ca sang', NULL),
(2, 'Tran Thi B', '1990-08-15', 'Nhan vien', 'TP HCM', FALSE, 'b@example.com', '0912345678', 10000000, '2021-05-10', 'Ca chieu', 1),
(3, 'Le Van C', '1992-09-22', 'Nhan vien', 'Da Nang', TRUE, 'c@example.com', '0934567890', 9500000, '2022-03-15', 'Ca dem', 1);
UPDATE NHANVIEN SET ChucVu='Lái Tàu' WHERE manv=3;
INSERT INTO NHANVIEN (MaNV, HoTen, NgaySinh, ChucVu, DiaChi, GioiTinh, Email, SoDT, Luong, NgayVaoLam, CaLam, MaNVQL) VALUES
(4, 'Pham Van D', '1988-11-20', 'Lái Tàu', 'Hai Phong', TRUE, 'd@example.com', '0971234567', 12000000, '2023-02-01', 'Ca sang', 1),
(5, 'Nguyen Thi E', '1991-04-10', 'Lái Tàu', 'Can Tho', FALSE, 'e@example.com', '0967654321', 11500000, '2023-06-15', 'Ca chieu', 1);

 
-- TAU
INSERT INTO TAU (MaTau, TenTau, SucChua) VALUES
(1, 'Tau SE1', 300),
(2, 'Tau TN2', 250);

-- GATAU
INSERT INTO GATAU (MaGT, TenGT, ViTri) VALUES
(1, 'Ga Ha Noi', 'Ha Noi'),
(2, 'Ga Da Nang', 'Da Nang'),
(3, 'Ga Sai Gon', 'TP HCM');

-- TUYEN
INSERT INTO TUYEN (MaTuyen, TenTuyen, DoDai) VALUES
(1, 'Ha Noi - Da Nang', 791),
(2, 'Da Nang - Sai Gon', 935);

-- CT_TUYEN
INSERT INTO CT_TUYEN (MaTuyen, MaGT, ThuTu) VALUES 
(1, 1, 1),
(1, 2, 2),
(2, 2, 1),
(2, 3, 2),
(1,3,3),
(2,1,3);

-- THUTUGA
INSERT INTO THUTUGA (MaTT, MaGaDi, MaGaDen, GiaTinhThem) VALUES
(1, 1, 2, 50000),
(2, 2, 3, 70000);

-- LICHTRINH
INSERT INTO LICHTRINH (MaLT, NgayKH, GioKH, TinhTrang, MaTau, MaNVLT, MaNVLL, SoLuongVeDB, MaTuyen) VALUES
(1, '2025-06-01', '08:00:00', 'Chay', 1, 1, 2, 100, 1),
(2, '2025-06-02', '09:00:00', 'Chay', 2, 1, 3, 80, 2);

-- LOAIVE
INSERT INTO LOAIVE (MaLoaiVe, MoTa, GiaGoc) VALUES
(1, 'Ve nguoi lon', 500000),
(2, 'Ve tre em', 300000);

-- TAIKHOAN
INSERT INTO TAIKHOAN (TenTK, MatKhau, MaNV) VALUES
('admin', '123456', 1),
('user1', 'password', 2);

-- ct-tuyen
INSERT INTO LICHDUNG (MaLT, MaGT, GioDen, GioDi, ThuTu) VALUES
(1, 1, '07:55:00', '08:00:00', 1), 
(1, 2, '09:00:00', '09:05:00', 2),
(1, 3, '09:50:00', '10:00:00', 3);
 -- Ga đến

INSERT INTO LICHDUNG (MaLT, MaGT, GioDen, GioDi, ThuTu) VALUES
(2, 2, '08:55:00', '09:00:00', 1), 
(2, 3, '10:00:00', '10:05:00', 2), 
(2, 1, '11:00:00', '11:07:00', 3);
