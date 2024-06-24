document.addEventListener("DOMContentLoaded", function() {
    const postId = location.pathname.split("/").pop();
    const editPostForm = document.getElementById("editPostForm");

    // Fetch and populate post data
    fetch(`/api/posts/${postId}`)
        .then(response => response.json())
        .then(data => {
            document.getElementById("title").value = data.title;
            document.getElementById("content").value = data.content;
            document.getElementById("tags").value = data.tags;
        })
        .catch(error => {
            console.error("Error fetching post:", error);
        });

    editPostForm.addEventListener("submit", function(event) {
        event.preventDefault();
        const formData = new FormData(editPostForm);
        const post = {
            title: formData.get("title"),
            content: formData.get("content"),
            tags: formData.get("tags"),
            published: false,
            publicPost: false,
        };

        fetch(`/api/posts/update/${postId}`, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(post)
        }).then(response => response.json())
            .then(data => {
                Swal.fire({
                    icon: 'success',
                    title: '수정 완료',
                    showConfirmButton: false,
                    timer: 1500
                }).then(() => {
                    location.href = '/drafts';
                });
            })
            .catch(error => {
                console.error("Error updating post:", error);
                Swal.fire({
                    icon: 'error',
                    title: '오류',
                    text: '수정 실패'
                });
            });
    });
});

function publishPost() {
    const editPostForm = document.getElementById("editPostForm");
    const postId = location.pathname.split("/").pop();
    const formData = new FormData(editPostForm);
    const post = {
        title: formData.get("title"),
        content: formData.get("content"),
        tags: formData.get("tags"),
        published: true,
        publicPost: true,
    };

    fetch(`/api/posts/update/${postId}`, {
        method: "PUT",
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
                location.href = '/home';
            });
        })
        .catch(error => {
            console.error("Error publishing post:", error);
            Swal.fire({
                icon: 'error',
                title: '오류',
                text: '출간 실패'
            });
        });
}

function saveDraft() {
    const editPostForm = document.getElementById("editPostForm");
    const postId = location.pathname.split("/").pop();
    const formData = new FormData(editPostForm);
    const post = {
        title: formData.get("title"),
        content: formData.get("content"),
        tags: formData.get("tags"),
        published: false,
        publicPost: false,
    };

    fetch(`/api/posts/update/${postId}`, {
        method: "PUT",
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
        })
        .catch(error => {
            console.error("Error saving draft:", error);
            Swal.fire({
                icon: 'error',
                title: '오류',
                text: '임시 저장 실패'
            });
        });
}
