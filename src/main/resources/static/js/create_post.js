document.addEventListener("DOMContentLoaded", function() {
    const postForm = document.getElementById("postForm");

    postForm.addEventListener("submit", function(event) {
        event.preventDefault();
        const formData = new FormData(postForm);
        const post = {
            title: formData.get("title"),
            content: formData.get("content"),
            tags: formData.get("tags"),
            published: false,
            publicPost: false,
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
                }).then(() => {
                    location.href = '/home';
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

function saveDraft() {
    const formData = new FormData(document.getElementById("postForm"));
    const post = {
        title: formData.get("title"),
        content: formData.get("content"),
        tags: formData.get("tags"),
        published: false,
        publicPost: false,
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
            }).then(() => {
                location.href = '/home';
            });
        }).catch(error => {
        console.error("Error saving post:", error);
        Swal.fire({
            icon: 'error',
            title: '오류',
            text: '임시 저장 실패'
        });
    });
}

function publishPost() {
    const formData = new FormData(document.getElementById("postForm"));
    const post = {
        title: formData.get("title"),
        content: formData.get("content"),
        tags: formData.get("tags"),
        published: true,
        publicPost: true,
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
                location.href = '/home';
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
