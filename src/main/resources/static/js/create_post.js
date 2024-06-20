document.addEventListener("DOMContentLoaded", function() {
    const postForm = document.getElementById("postForm");

    postForm.addEventListener("submit", function(event) {
        event.preventDefault();
        createOrUpdatePost(false);
    });
});

function publishPost() {
    createOrUpdatePost(true);
}

function createOrUpdatePost(isPublished) {
    const formData = new FormData(document.getElementById("postForm"));
    formData.append("published", isPublished);

    fetch("/users/loggedInUser")
        .then(response => response.json())
        .then(user => {
            const post = {
                title: formData.get("title"),
                content: formData.get("content"),
                published: isPublished,
                publicPost: true, // 원하는 값으로 설정
                author: { username: user.username } // 세션에서 가져온 사용자 정보
            };

            fetch("/posts/create", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(post)
            })
                .then(response => response.json())
                .then(data => {
                    if (data.id) {
                        Swal.fire({
                            icon: 'success',
                            title: '글 작성 성공',
                            showConfirmButton: false,
                            timer: 1500
                        }).then(() => {
                            location.href = '/home';
                        });
                    } else {
                        throw new Error("글 작성 실패");
                    }
                })
                .catch(error => {
                    Swal.fire({
                        icon: 'error',
                        title: '오류',
                        text: '글 작성 실패: ' + error.message,
                    });
                });
        })
        .catch(error => {
            console.error("Error fetching logged in user:", error);
        });
}
