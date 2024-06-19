async function checkUsernameAvailability() {
    const username = document.getElementById("username").value.trim();
    if (!username) {
        Swal.fire({
            icon: 'error',
            title: '오류',
            text: 'ID를 입력하세요.',
        });
        return false;
    }

    Swal.fire({
        title: '잠시만 기다려 주세요...',
        allowOutsideClick: false,
        didOpen: () => {
            Swal.showLoading();
        }
    });

    try {
        const response = await fetch(`/users/check-username?username=${username}`);
        if (!response.ok) {
            throw new Error('네트워크 응답이 올바르지 않습니다.');
        }
        const isAvailable = await response.json();
        Swal.close();

        if (isAvailable) {
            Swal.fire({
                icon: 'error',
                title: '아이디가 이미 사용 중입니다.',
            });
            return false;
        } else {
            Swal.fire({
                icon: 'success',
                title: '아이디 사용 가능합니다.',
            });
            return true;
        }
    } catch (error) {
        console.error("Error checking username availability:", error);
        Swal.fire({
            icon: 'error',
            title: '오류 발생',
            text: '아이디 중복 확인 중 오류가 발생했습니다.',
        });
        return false;
    }
}

async function checkEmailAvailability() {
    const email = document.getElementById("email").value.trim();
    if (!email) {
        Swal.fire({
            icon: 'error',
            title: '오류',
            text: 'E-mail을 입력하세요.',
        });
        return false;
    }

    Swal.fire({
        title: '잠시만 기다려 주세요...',
        allowOutsideClick: false,
        didOpen: () => {
            Swal.showLoading();
        }
    });

    try {
        const response = await fetch(`/users/check-email?email=${email}`);
        if (!response.ok) {
            throw new Error('네트워크 응답이 올바르지 않습니다.');
        }
        const isAvailable = await response.json();
        Swal.close();

        if (isAvailable) {
            Swal.fire({
                icon: 'error',
                title: '이메일이 이미 사용 중입니다.',
            });
            return false;
        } else {
            Swal.fire({
                icon: 'success',
                title: '이메일 사용 가능합니다.',
            });
            return true;
        }
    } catch (error) {
        console.error("Error checking email availability:", error);
        Swal.fire({
            icon: 'error',
            title: '오류 발생',
            text: '이메일 중복 확인 중 오류가 발생했습니다.',
        });
        return false;
    }
}

async function register() {
    const username = document.getElementById("username").value.trim();
    const password = document.getElementById("password").value.trim();
    const confirmPassword = document.getElementById("confirmPassword").value.trim();
    const name = document.getElementById("name").value.trim();
    const email = document.getElementById("email").value.trim();

    try {
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
                title: '오류',
                text: '비밀번호가 일치하지 않습니다.',
            });
            return;
        }

        const isUsernameAvailable = await checkUsernameAvailability(username);
        if (!isUsernameAvailable) {
            return;
        }

        const isEmailAvailable = await checkEmailAvailability(email);
        if (!isEmailAvailable) {
            return;
        }

        Swal.fire({
            title: '회원가입 중...',
            allowOutsideClick: false,
            didOpen: () => {
                Swal.showLoading();
            }
        });

        const response = await fetch("/users/register", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({ username, password, name, email })
        });

        if (!response.ok) {
            throw new Error("회원가입 실패");
        }

        const data = await response.json();
        Swal.fire({
            icon: 'success',
            title: '회원가입 성공',
        }).then(() => {
            window.location.href = data.redirectUrl; // 회원 가입 성공 후 리다이렉트
        });

    } catch (error) {
        console.error("Error during registration:", error);
        Swal.fire({
            icon: 'error',
            title: '회원가입 실패',
            text: error.message,
        });
    }
}
