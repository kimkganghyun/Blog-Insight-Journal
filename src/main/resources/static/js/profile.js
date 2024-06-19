document.addEventListener("DOMContentLoaded", function() {
    const form = document.getElementById("profileForm");
    const urlParams = new URLSearchParams(window.location.search);
    const username = urlParams.get('username');
    document.getElementById("username").value = username;
    document.getElementById("username").readOnly = true; // ID 필드 수정 불가능

    form.addEventListener("submit", function(event) {
        event.preventDefault(); // 폼 제출 막기

        const formData = new FormData(form);

        Swal.fire({
            title: '저장 중...',
            allowOutsideClick: false,
            didOpen: () => {
                Swal.showLoading();
            }
        });

        fetch("/users/profile", {
            method: "POST",
            body: formData
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error("프로필 저장 실패");
                }
                return response.json();
            })
            .then(data => {
                Swal.close();
                Swal.fire({
                    icon: 'success',
                    title: '프로필 저장 성공',
                }).then(() => {
                    window.location.href = "/home"; // 홈으로 리다이렉트
                });
            })
            .catch(error => {
                Swal.close();
                Swal.fire({
                    icon: 'error',
                    title: '오류 발생',
                    text: error.message,
                });
            });
    });
});
