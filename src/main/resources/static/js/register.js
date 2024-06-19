function checkUsernameAvailability() {
    const username = document.getElementById("username").value;
    if (!username) {
        Swal.fire({
            icon: 'error',
            title: '오류',
            text: 'ID를 입력하세요.',
        });
        return;
    }
    fetch(`/users/check-username?username=${username}`)
        .then(response => response.json())
        .then(isAvailable => {
            if (isAvailable) {
                Swal.fire({
                    icon: 'error',
                    title: '아이디가 이미 사용 중입니다.',
                });
            } else {
                Swal.fire({
                    icon: 'success',
                    title: '아이디 사용 가능합니다.',
                });
            }
        })
        .catch(error => {
            console.error("Error checking username availability:", error);
            Swal.fire({
                icon: 'error',
                title: '오류 발생',
                text: '아이디 중복 확인 중 오류가 발생했습니다.',
            });
        });
}

function checkEmailAvailability() {
    const email = document.getElementById("email").value;
    if (!email) {
        Swal.fire({
            icon: 'error',
            title: '오류',
            text: 'E-mail을 입력하세요.',
        });
        return;
    }
    fetch(`/users/check-email?email=${email}`)
        .then(response => response.json())
        .then(isAvailable => {
            if (isAvailable) {
                Swal.fire({
                    icon: 'error',
                    title: '이메일이 이미 사용 중입니다.',
                });
            } else {
                Swal.fire({
                    icon: 'success',
                    title: '이메일 사용 가능합니다.',
                });
            }
        })
        .catch(error => {
            console.error("Error checking email availability:", error);
            Swal.fire({
                icon: 'error',
                title: '오류 발생',
                text: '이메일 중복 확인 중 오류가 발생했습니다.',
            });
        });
}

function register() {
    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;
    const confirmPassword = document.getElementById("confirmPassword").value;
    const name = document.getElementById("name").value;
    const email = document.getElementById("email").value;

    if (!username || !password || !confirmPassword || !name || !email) {
        Swal.fire({
            icon: 'error',
            title: '오류',
            text: '모든 필드를 입력하세요.',
        });
        return;
    }

    if (password !== confirmPassword) {
        Swal.fire({
            icon: 'error',
            title: '비밀번호가 일치하지 않습니다.',
        });
        return;
    }

    fetch("/users/register", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({username, password, name, email})
    })
        .then(response => {
            if (response.ok) {
                return response.json();
            } else {
                throw new Error("회원가입 실패");
            }
        })
        .then(data => {
            Swal.fire({
                icon: 'success',
                title: '회원가입 성공',
            }).then(() => {
                window.location.href = data.redirectUrl;
            });
        })
        .catch(error => {
            Swal.fire({
                icon: 'error',
                title: '회원가입 실패',
                text: error.message,
            });
        });
}
