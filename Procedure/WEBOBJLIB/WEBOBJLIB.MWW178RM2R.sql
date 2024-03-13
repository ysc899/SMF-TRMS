DROP PROCEDURE WEBOBJLIB.MWW178RM2R;

CREATE PROCEDURE WEBOBJLIB.MWW178RM2R (
     In    I_COR     char(03),        --회사코드
     In    I_UID     char(12),        -- User ID
     In    I_IP      char(30),        -- IP
     Out   O_MSGCOD  char(3) ,        -- Message ID
     Out   O_ERRCOD  char(10),        -- Exception error
     In    I_DGN      char(1),        --접수일자구분
     In    I_FDT   decimal(8,0),      --접수일자FROM
     In    I_TDT   decimal(8,0),      --접수일자TO
     In    I_NAM      char(30),       --수진자명
     In    I_RGN1     char(1) ,       --일반구분(N)
     In    I_RGN2     char(1) ,       --조직구분(C)
     In    I_RGN3     char(1) ,       --세포여부(C)
     In    I_HAK      char(1),        --학부구분
     In    I_STS      char(1),        --상태구분
     In    I_PCHN     char(15),       --챠트번호
     In    I_PETC     char(50),       --기타기록（검체번호）
     In    I_PJKNF    char(1) ,       --진료과(1)/진료의사(2)flag
     In    I_PJKN     char(20),       --진료과/진료의사
     In    I_PECF     char(1) ,       --씨젠사용자FLAG:'E'
     In    I_PHOS     char(5) ,       --병원코드_씨젠사용자인경우
     In    I_PINQ     char(1) ,       --  Y =수진자명，챠트번호　조건
     In    I_IGBN     char(1) ,       --조회구분
     In    I_IDAT  decimal(8,0),      --접수일자
     In    I_IJNO  decimal(5,0),      --접수번호
 --Pageing Parm
     In    I_ICNT  decimal(3,0),      --읽을갯수
     In    I_ROW   decimal(8,0)       --시작ROW
     )
     EXTERNAL NAME WEBOBJLIB.MWW178RM2R LANGUAGE RPGLE GENERAL
     
     COMMENT ON PROCEDURE WEBOBJLIB.MWW178RM2R IS
           '홈페이지　전용　환자리스트　MASTER '