DROP TABLE SIWON.student;

CREATE TABLE SIWON.student (
    st_num VARCHAR2(10),        --�й�
    st_name VARCHAR2(20) NOT NULL,          --�̸�
    st_phone VARCHAR2(15) NOT NULL,         --��ȭ��ȣ
    st_ad VARCHAR2(20),                     --�ּ�
    st_age NUMBER(3),                       --����
    st_money NUMBER(7) DEFAULT 116000,       --����� (�鸸�� ����)
    sub_num VARCHAR2(10),                   --�ܷ�Ű
    
    CONSTRAINT fk_s_s FOREIGN KEY (sub_num) REFERENCES SIWON.subject(sub_num),
    CONSTRAINT pk_s_s PRIMARY KEY (st_num,sub_num)
    
    );
    
    