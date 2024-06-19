document.addEventListener("DOMContentLoaded", function() {
    const form = document.getElementById("profileForm");
    const urlParams = new URLSearchParams(window.location.search);
    const username = urlParams.get('username');
    document.getElementById("username").value = username;

    form.addEventListener("submit", function(event) {
        event.preventDefault(); // 폼 제출 막기

        const formData = new FormData(form);

        fetch("/users/profile", {
            method: "POST",
            body: formData
        })
            .then(response => response.json())
            .then(data => {
                if (data.redirectUrl) {
                    alert("프로필 저장 성공");
                    window.location.href = data.redirectUrl;
                } else {
                    throw new Error("프로필 저장 실패");
                }
            })
            .catch(error => {
                alert(error.message);
            });
    });
});
