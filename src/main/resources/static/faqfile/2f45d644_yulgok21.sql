DROP TABLE SIWON.student;

CREATE TABLE SIWON.student (
    st_num VARCHAR2(10),        --학번
    st_name VARCHAR2(20) NOT NULL,          --이름
    st_phone VARCHAR2(15) NOT NULL,         --전화번호
    st_ad VARCHAR2(20),                     --주소
    st_age NUMBER(3),                       --나이
    st_money NUMBER(7) DEFAULT 116000,       --장려금 (백만원 이하)
    sub_num VARCHAR2(10),                   --외래키
    
    CONSTRAINT fk_s_s FOREIGN KEY (sub_num) REFERENCES SIWON.subject(sub_num),
    CONSTRAINT pk_s_s PRIMARY KEY (st_num,sub_num)
    
    );
    
    