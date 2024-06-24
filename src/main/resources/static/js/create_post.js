document.addEventListener("DOMContentLoaded", function() {
    const postForm = document.getElementById("postForm");

    postForm.addEventListener("submit", function(event) {
        event.preventDefault();
        saveDraft(); // 임시 저장 함수 호출
    });
});

function saveDraft() {
    const postForm = document.getElementById("postForm");
    const formData = new FormData(postForm);
    const post = {
        title: formData.get("title"),
        content: formData.get("content"),
        tags: formData.get("tags"),
        published: false,
        publicPost: false,
        draft: true
    };

    fetch("/api/posts/create", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(post)
    }).then(response => response.json())
        .then(data => {
            Swal.fire({
                icon: 'success',
                title: '임시 저장 완료',
                showConfirmButton: false,
                timer: 1500
            });
            loadDrafts(); // 임시 저장 후 임시 글 목록을 다시 로드합니다.
        }).catch(error => {
        console.error("Error saving draft:", error);
        Swal.fire({
            icon: 'error',
            title: '오류',
            text: '임시 저장 실패'
        });
    });
}

function publishPost() {
    const postForm = document.getElementById("postForm");
    const formData = new FormData(postForm);
    const post = {
        title: formData.get("title"),
        content: formData.get("content"),
        tags: formData.get("tags"),
        published: true,
        publicPost: true,
        draft: false
    };

    fetch("/api/posts/create", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(post)
    }).then(response => response.json())
        .then(data => {
            Swal.fire({
                icon: 'success',
                title: '출간 완료',
                showConfirmButton: false,
                timer: 1500
            }).then(() => {
                location.href = '/home'; // 출간 후 홈으로 리다이렉트합니다.
            });
        }).catch(error => {
        console.error("Error publishing post:", error);
        Swal.fire({
            icon: 'error',
            title: '오류',
            text: '출간 실패'
        });
    });
}

function loadDrafts() {
    fetch("/users/loggedInUser")
        .then(response => response.json())
        .then(data => {
            if (data.username) {
                const username = data.username;
                fetch(`/api/posts/drafts?username=${username}`)
                    .then(response => response.json())
                    .then(posts => {
                        const draftsContainer = document.getElementById("draftsContainer");
                        draftsContainer.innerHTML = "";
                        posts.forEach(post => {
                            const postElement = document.createElement("div");
                            postElement.classList.add("draft-post");

                            const titleElement = document.createElement("h2");
                            titleElement.textContent = post.title;
                            postElement.appendChild(titleElement);

                            const contentElement = document.createElement("p");
                            contentElement.textContent = post.content;
                            postElement.appendChild(contentElement);

                            const editButton = document.createElement("button");
                            editButton.textContent = "수정";
                            editButton.addEventListener("click", function() {
                                location.href = `/edit-post/${post.postId}`;
                            });
                            postElement.appendChild(editButton);

                            draftsContainer.appendChild(postElement);
                        });
                    })
                    .catch(error => {
                        console.error("Error fetching drafts:", error);
                    });
            }
        })
        .catch(error => {
            console.error("Error fetching logged in user:", error);
        });
}
