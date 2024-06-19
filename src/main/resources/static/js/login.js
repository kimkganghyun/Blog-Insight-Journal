function login() {
    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;

    if (!username || !password) {
        Swal.fire({
            icon: 'error',
            title: '오류',
            text: 'ID와 PW를 입력하세요.',
        });
        return;
    }

    fetch("/users/login", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({username, password})
    })
        .then(response => {
            if (response.ok) {
                return response.json();
            } else {
                throw new Error("로그인 실패");
            }
        })
        .then(data => {
            Swal.fire({
                icon: 'success',
                title: '로그인 성공',
            }).then(() => {
                window.location.href = "/home"; // 로그인 후 이동할 페이지
            });
        })
        .catch(error => {
            Swal.fire({
                icon: 'error',
                title: '로그인 실패',
                text: error.message,
            });
        });
}
