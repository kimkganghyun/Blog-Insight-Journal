document.addEventListener("DOMContentLoaded", function() {
    const authButtons = document.getElementById("authButtons");
    const userMenu = document.getElementById("userMenu");
    const usernameDisplay = document.getElementById("usernameDisplay");
    const newPostButton = document.getElementById("newPostButton");

    // 로그인된 사용자 이름을 서버에서 가져오기
    fetch("/users/loggedInUser")
        .then(response => response.json())
        .then(data => {
            if (data.username) {
                usernameDisplay.textContent = data.username + "님";
                authButtons.style.display = "none";
                userMenu.style.display = "inline-block";
                newPostButton.style.display = "inline-block";
            }
        })
        .catch(error => {
            console.error("Error fetching logged in user:", error);
        });

    // 드롭다운 메뉴 토글
    userMenu.addEventListener("click", function(event) {
        event.stopPropagation();
        const dropdownMenu = document.getElementById("userDropdown");
        dropdownMenu.style.display = dropdownMenu.style.display === "block" ? "none" : "block";
    });

    // 페이지 외부 클릭 시 드롭다운 메뉴 닫기
    window.addEventListener("click", function() {
        const dropdownMenu = document.getElementById("userDropdown");
        dropdownMenu.style.display = "none";
    });
});

function logout() {
    fetch("/users/logout", {
        method: "POST"
    }).then(response => {
        if (response.ok) {
            Swal.fire({
                icon: 'success',
                title: '로그아웃 성공',
                showConfirmButton: false,
                timer: 1500
            }).then(() => {
                location.href = '/home';
            });
        } else {
            Swal.fire({
                icon: 'error',
                title: '오류',
                text: '로그아웃 실패',
            });
        }
    }).catch(error => {
        Swal.fire({
            icon: 'error',
            title: '오류',
            text: '로그아웃 실패: ' + error.message,
        });
    });
}
