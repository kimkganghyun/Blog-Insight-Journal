function login() {
    const username = document.getElementById("username").value.trim();
    const password = document.getElementById("password").value.trim();

    fetch("/users/login", {
        method: "POST",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded"
        },
        body: new URLSearchParams({
            username: username,
            password: password
        })
    })
        .then(response => {
            if (response.ok) {
                return response.json();
            } else {
                return response.json().then(error => {
                    throw new Error(error.message || '로그인 실패');
                });
            }
        })
        .then(data => {
            Swal.fire({
                icon: 'success',
                title: '로그인 성공',
                showConfirmButton: false,
                timer: 1500
            }).then(() => {
                location.href = data.redirectUrl;
            });
        })
        .catch(error => {
            Swal.fire({
                icon: 'error',
                title: '오류',
                text: error.message,
            });
        });
}
