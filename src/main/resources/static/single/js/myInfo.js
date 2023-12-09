function memD() {

    if (confirm("탈퇴하시겠습니까?")) {
        // 확인 버튼 클릭 시 동작
        alert("탈퇴됐습니다.");
        location.href = 'mDelete?MId=[[${member.MId}]]';
    } else {
        // 취소 버튼 클릭 시 동작
        alert("탈퇴를 취소했습니다.");
    }

}

function pwchange() {
    const elem = document.getElementById("chPassWord");

    elem.innerHTML = "<input type='password' name='nowPw' placeholder='현재 비밀번호' id='nowPw'/><br/>"
        + "<input type='password' name='newPw' placeholder='새 비밀번호' id='newPw'/><br/>"
        + "<input type='password' name='newPw2' placeholder='새 비밀번호 확인'id='newPw2'/><br/>"
        + "<button onclick='pwchange2()'>확인</button>";
}

function pwchange2() {
    let check = confirm('변경하시겠습니까?');
    if (check) {
        let MId = document.getElementById("MId").value;
        let nowPw = document.getElementById("nowPw").value;
        let newPw = document.getElementById("newPw").value;
        let newPw2 = document.getElementById("newPw2").value;
        console.log(MId);
        if (newPw == newPw2) {
            $.ajax({
                type: "POST",
                url: "changePw",
                data: {
                    "MId": MId,
                    "nowPw": nowPw,
                    "newPw": newPw
                },
                dataType: "text",
                success: function (result) {
                    if (result == "OK") {
                        // 사용할 수 있는 아이디일 경우 실행
                        alert('비밀번호가 변경되었습니다!');

                    } else if(result = "pn"){
                        // 사용할 수 없는 아이디일 경우 실행
                        alert('현재 비밀번호가 일치하지 않습니다.');
                    } else {
                        alert('서버 오류로 인해 변경에 실패 했습니다.');
                    }//er fail (오류)
                },
                error: function () {
                    alert("오류로 인해 변경에 실패 했습니다.");

                }

            });
        }//비밀 번호가 다를때 실행
        else {
            alert("두 비밀 번호가 일치 하지 않습니다");
        }
    } else {

    }

}
