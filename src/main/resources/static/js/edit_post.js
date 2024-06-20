document.addEventListener("DOMContentLoaded", function() {
    const postId = window.location.pathname.split("/").pop();
    loadPost(postId);

    const editPostForm = document.getElementById("editPostForm");

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
                    title: '임시 저장 완료',
                    showConfirmButton: false,
                    timer: 1500
                }).then(() => {
                    location.href = '/posts/drafts';
                });
            }).catch(error => {
            console.error("Error saving post:", error);
            Swal.fire({
                icon: 'error',
                title: '오류',
                text: '임시 저장 실패'
            });
        });
    });
});

function loadPost(postId) {
    fetch(`/api/posts/${postId}`)
        .then(response => response.json())
        .then(post => {
            document.getElementById("title").value = post.title;
            document.getElementById("content").value = post.content;
            document.getElementById("tags").value = post.tags;
        })
        .catch(error => {
            console.error("Error loading post:", error);
            Swal.fire({
                icon: 'error',
                title: '오류',
                text: '글 로딩 실패'
            });
        });
}

function saveDraft() {
    const formData = new FormData(document.getElementById("editPostForm"));
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
            }).then(() => {
                location.href = '/posts/drafts';
            });
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
    const formData = new FormData(document.getElementById("editPostForm"));
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
                location.href = '/posts/drafts';
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
