<!DOCTYPE html>
<script>
    var token = '{{ csrf_token() }}';
    var url = '{{ config('app.url') }}/api/v1/opinion/get'
    var data = '{"{{ \App\Models\User::EMAIL }}" : "1connect.5.2017@gmail.com", "{{ \App\Models\User::PASS }}" : "{{  hash(config('app.hash'), 'passpass') }}"}';
    var request = new XMLHttpRequest();
    request.open('POST', url);
    request.setRequestHeader('X-CSRF-TOKEN', token);
    request.onreadystatechange = function () {
        console.log(request.responseText);
    };
    request.setRequestHeader('Content-Type', 'application/json');
    request.send(data);
</script>
</html>