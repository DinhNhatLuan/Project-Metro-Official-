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
    DoDai INT
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
    MaNV INT NOT NULL,
    SoLuongVeDB INT,
    MaTuyen INT NOT NULL
);

CREATE TABLE LICHDUNG (
    MaLT INT NOT NULL,
    MaGT INT NOT NULL,
    GioDen DATETIME,
    GioDi DATETIME,
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
ADD CONSTRAINT fk_ltrinh_nv
FOREIGN KEY (MaNV) REFERENCES NHANVIEN(MaNV);

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
insert into NHANVIEN (HoTen,NgaySinh,ChucVu,DiaChi,GioiTinh,Email,SoDT,Luong,NgayVaoLam,CaLam,MaNVQL) values('Bombardiro Crocodilo','1980-1-1','Nhân viên quản lý','Bình Dương',1,
'captainman@gmail.com','1234567',1000000, '2000-1-1','Sáng',null);
insert into TAIKHOAN values('tkadmin001','123456',1);
