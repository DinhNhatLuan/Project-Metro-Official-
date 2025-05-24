Create table TAIKHOAN (
TenTK varchar(50) not null,
MatKhau varchar(50),
MaNV varchar(10) not null
);
Create table NHANVIEN (
MaNV varchar(10) not null,
HoTen varchar(50) not null,
ChucVu varchar(50),
DiaChi varchar(50),
GioiTinh boolean,
Email varchar(50),
SoDT varchar(20),
Luong int,
NgayVaoLam date,
CaLam varchar(20),
MaNVQL varchar(10)
);

Create table VE (
MaVe varchar(10) not null,
Gia int,
MaLoaiVe varchar(20) not null,
ThoiDiemMua datetime,
MaLT varchar(10),
MaTT varchar(10)
);
Create table GATAU (
MaGT varchar(10) not null,
TenGT varchar(50), 
ViTri varchar(50)
);
Create table THUTUGA (
MaTT varchar(10) not null,
MaGaDi varchar(10),
MaGaDen varchar(10),
GiaTinhThem int not null
);
Create table LOAIVE (
MaLoaiVe varchar(10),
MoTa varchar(100),
GiaGoc int
);
Create table LICHTRINH (
MaLT varchar(10),
NgayKH date,
GioKH time,
TinhTrang varchar(10),
MaTau varchar(10),
MaNV varchar(10) not null, 
SoLuongVeDB int 
);
Create table LICHDUNG (
MaLT varchar(10) not null,
MaGT varchar(10) not null,
GioDen datetime,
GioDi datetime,
ThuTu int
);
Create table TAU (
MaTau varchar(10) not null,
TenTau varchar(30),
SucChua int
);

alter table TAIKHOAN add constraint tkhoan_pk primary key(TenTK);
alter table NHANVIEN add constraint nvien_pk primary key(MaNV);
alter table VE add constraint ve_pk primary key(MaVe);
alter table GATAU add constraint gtau_pk primary key(MaGT);
alter table THUTUGA add constraint ttga_pk primary key(MaTT);
alter table LOAIVE add constraint loaive_pk primary key(MaLoaiVe);
alter table LICHTRINH add constraint ltrinh_pk primary key(MaLT);
alter table LICHDUNG add constraint ldung_pk primary key(MaLT,MaGT);
alter table TAU add constraint tau_pk primary key(MaTau);


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


-- Xóa bảng có phụ thuộc khóa ngoại trước
DROP TABLE IF EXISTS LICHDUNG;
DROP TABLE IF EXISTS VE;
DROP TABLE IF EXISTS TAIKHOAN;
DROP TABLE IF EXISTS LICHTRINH;
DROP TABLE IF EXISTS THUTUGA;
DROP TABLE IF EXISTS NHANVIEN;
DROP TABLE IF EXISTS TAU;
DROP TABLE IF EXISTS GATAU;
DROP TABLE IF EXISTS LOAIVE;


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

//Nhap du lieu
insert into NHANVIEN values('nv001','Bombardiro Crocodilo','Nhân viên quản lý','Bình Dương',1,
'captainman@gmail.com','1234567',1000000, '2000-1-1','Sáng',null);
insert into TAIKHOAN values('tkadmin001','123456','nv001');
