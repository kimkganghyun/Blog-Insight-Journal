document.getElementById("commentForm").addEventListener("submit", function(event) {
    event.preventDefault();
    saveComment();
});

function saveComment() {
    const formData = new FormData(document.getElementById("commentForm"));

    fetch("/comments/save", {
        method: "POST",
        body: formData
    })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                Swal.fire({
                    icon: 'success',
                    title: '댓글이 작성되었습니다.',
                    showConfirmButton: false,
                    timer: 1500
                });
                // 댓글 목록 갱신
                loadComments();
            } else {
                Swal.fire({
                    icon: 'error',
                    title: '댓글 작성에 실패했습니다.',
                    text: data.message,
                });
            }
        });
}

function loadComments() {
    fetch("/comments")
        .then(response => response.json())
        .then(data => {
            const commentList = document.getElementById("commentList");
            commentList.innerHTML = "";
            data.comments.forEach(comment => {
                const commentItem = document.createElement("div");
                commentItem.textContent = comment.content;
                commentList.appendChild(commentItem);
            });
        });
}
