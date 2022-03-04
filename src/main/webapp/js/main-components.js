Vue.component(
    'result-table',
    {
        template:
            `
                <div>
                    <h1 class="col-xs-1 text-center">Music Bands</h1>
                    <table class="table table-striped table-bordered table-hover p-5 text-center">
                        <tr>
                            <th>Id</th>
                            <th>Name</th>
                            <th>X</th>
                            <th>Y</th>
                            <th>Creation date</th>
                            <th>Number Of Participants</th>
                            <th>Albums Count</th>
                            <th>Genre</th>
                            <th>Label Name</th>
                            <th>Actions</th>
                        </tr>
                        <tr v-for="band in bands">
                            <td>{{ band.id }}</td>
                            <td>{{ band.name }}</td>
                            <td>{{ band.x }}</td>
                            <td>{{ band.y }}</td>
                            <td>{{ formatCreationDate(band) }}</td>
                            <td>{{ band.numberOfParticipants }}</td>
                            <td>{{ band.albumsCount }}</td>
                            <td>{{ band.genre }}</td>
                            <td>{{ band.labelName }}</td>
                            <td>
                                <button class="btn btn-success" v-on:click="editBand(band)" type="submit">Edit</button>
                                <button class="btn btn-danger" v-on:click="deleteBand(band)" type="submit">Delete</button>
                            </td>
                        </tr>
                    </table>
                </div>
            `,

        props: ["bands"],
        methods: {
            editBand: function (band) {
                this.$emit('editband', band);
            },
            deleteBand: function (band) {
                this.$emit('deleteband', band);
            },
            formatCreationDate: function (band) {
                return moment(band.creationDate).format('YYYY-MM-DD');
            }
        }
    }
);

Vue.component(
    'filters',
    {
        template:
            `
                <div>
                    <h1 class="col-xs-1 text-center">Options</h1>
                    
                    <h3>Pagination</h3>
                    <div>
                        <label for="elementsCount">Elements count:</label>
                        <input id="elementsCount" type="text" maxlength="8" v-model="elementsCount">
                        <label for="pageNumber" class="ml-5">Page number</label>
                        <input id="pageNumber" type="text" maxlength="8" v-model="pageNumber">
                    </div>
                    
                    <h3>Order by 1</h3>
                    <div>
                        <label for="orderById1">Id</label>
                        <input id="orderById1" type="radio" value="id" v-model="orderBy1">
                        <label for="orderByName1" class="pl-3">Name</label>
                        <input id="orderByName1" type="radio" value="name" v-model="orderBy1">
                        <label for="orderByX1" class="pl-3">X</label>
                        <input id="orderByX1" type="radio" value="x" v-model="orderBy1">
                        <label for="orderByY1" class="pl-3">Y</label>
                        <input id="orderByY1" type="radio" value="y" v-model="orderBy1">
                        <label for="orderByCreationDate1" class="pl-3">Creation date</label>
                        <input id="orderByCreationDate1" type="radio" value="creationDate" v-model="orderBy1">     
                        <label for="orderByNumberOfParticipantsCount1" class="pl-3">Number Of Participants</label>
                        <input id="orderBynNumberOfParticipantsCount1" type="radio" value="numberOfParticipants" v-model="orderBy1">
                        <label for="orderByAlbumsCount1" class="pl-3">Albums Count</label>
                        <input id="orderByAlbumsCount1" type="radio" value="albumsCount" v-model="orderBy1">
                        <label for="orderByGenre1" class="pl-3">Genre</label>
                        <input id="orderByGenre1" type="radio" value="genre" v-model="orderBy1">
                        <label for="orderByLabelName1" class="pl-3">Label Name</label>
                        <input id="orderByLabelName1" type="radio" value="labelName" v-model="orderBy1">
                        <label for="orderByNone1" class="pl-3">None</label>
                        <input id="orderByNone1" type="radio" value="" v-model="orderBy1" checked>
                        
                        <label for="orderDirectionAsc1" class="ml-5">Asc</label>
                        <input id="orderDirectionAsc1" type="radio" value="a" v-model="orderDirection1">
                        <label for="orderDirectionDesc1" class="pl-3">Desc</label>
                        <input id="orderDirectionDesc1" type="radio" value="d" v-model="orderDirection1">
                        <label for="orderDirectionNone1" class="pl-3">None</label>
                        <input id="orderDirectionNone1" type="radio" value="" v-model="orderDirection1">
                    </div>
                    
                    <h3>Order by 2</h3>
                    <div>
                        <label for="orderById2">Id</label>
                        <input id="orderById2" type="radio" value="id" v-model="orderBy2">
                        <label for="orderByName2" class="pl-3">Name</label>
                        <input id="orderByName2" type="radio" value="name" v-model="orderBy2">
                        <label for="orderByX2" class="pl-3">X</label>
                        <input id="orderByX2" type="radio" value="x" v-model="orderBy2">
                        <label for="orderByY2" class="pl-3">Y</label>
                        <input id="orderByY2" type="radio" value="y" v-model="orderBy2">
                        <label for="orderByCreationDate2" class="pl-3">Creation date</label>
                        <input id="orderByCreationDate2" type="radio" value="creationDate" v-model="orderBy2">     
                        <label for="orderByNumberOfParticipantsCount2" class="pl-3">Number Of Participants</label>
                        <input id="orderBynNumberOfParticipantsCount2" type="radio" value="numberOfParticipants" v-model="orderBy2">
                        <label for="orderByAlbumsCount2" class="pl-3">Albums Count</label>
                        <input id="orderByAlbumsCount2" type="radio" value="albumsCount" v-model="orderBy2">
                        <label for="orderByGenre2" class="pl-3">Genre</label>
                        <input id="orderByGenre2" type="radio" value="genre" v-model="orderBy2">
                        <label for="orderByLabelName2" class="pl-3">Label Name</label>
                        <input id="orderByLabelName2" type="radio" value="labelName" v-model="orderBy2">
                        <label for="orderByNone2" class="pl-3">None</label>
                        <input id="orderByNone2" type="radio" value="" v-model="orderBy2" checked>
                        
                        <label for="orderDirectionAsc2" class="ml-5">Asc</label>
                        <input id="orderDirectionAsc2" type="radio" value="a" v-model="orderDirection2">
                        <label for="orderDirectionDesc2" class="pl-3">Desc</label>
                        <input id="orderDirectionDesc2" type="radio" value="d" v-model="orderDirection2">
                        <label for="orderDirectionNone2" class="pl-3">None</label>
                        <input id="orderDirectionNone2" type="radio" value="" v-model="orderDirection2">
                    </div>
                    
                    <h3>Order by 3</h3>
                    <div>
                        <label for="orderById3">Id</label>
                        <input id="orderById3" type="radio" value="id" v-model="orderBy3">
                        <label for="orderByName3" class="pl-3">Name</label>
                        <input id="orderByName3" type="radio" value="name" v-model="orderBy3">
                        <label for="orderByX3" class="pl-3">X</label>
                        <input id="orderByX3" type="radio" value="x" v-model="orderBy3">
                        <label for="orderByY3" class="pl-3">Y</label>
                        <input id="orderByY3" type="radio" value="y" v-model="orderBy3">
                        <label for="orderByCreationDate3" class="pl-3">Creation date</label>
                        <input id="orderByCreationDate3" type="radio" value="creationDate" v-model="orderBy3">     
                        <label for="orderByNumberOfParticipantsCount3" class="pl-3">Number Of Participants</label>
                        <input id="orderBynNumberOfParticipantsCount3" type="radio" value="numberOfParticipants" v-model="orderBy3">
                        <label for="orderByAlbumsCount3" class="pl-3">Albums Count</label>
                        <input id="orderByAlbumsCount3" type="radio" value="albumsCount" v-model="orderBy3">
                        <label for="orderByGenre3" class="pl-3">Genre</label>
                        <input id="orderByGenre3" type="radio" value="genre" v-model="orderBy3">
                        <label for="orderByLabelName3" class="pl-3">Label Name</label>
                        <input id="orderByLabelName3" type="radio" value="labelName" v-model="orderBy3">
                        <label for="orderByNone3" class="pl-3">None</label>
                        <input id="orderByNone3" type="radio" value="" v-model="orderBy3" checked>
                        
                        <label for="orderDirectionAsc3" class="ml-5">Asc</label>
                        <input id="orderDirectionAsc3" type="radio" value="a" v-model="orderDirection3">
                        <label for="orderDirectionDesc3" class="pl-3">Desc</label>
                        <input id="orderDirectionDesc3" type="radio" value="d" v-model="orderDirection3">
                        <label for="orderDirectionNone3" class="pl-3">None</label>
                        <input id="orderDirectionNone3" type="radio" value="" v-model="orderDirection3">
                    </div>
                    
                    <h3>Order by 4</h3>
                    <div>
                        <label for="orderById4">Id</label>
                        <input id="orderById4" type="radio" value="id" v-model="orderBy4">
                        <label for="orderByName4" class="pl-3">Name</label>
                        <input id="orderByName4" type="radio" value="name" v-model="orderBy4">
                        <label for="orderByX4" class="pl-3">X</label>
                        <input id="orderByX4" type="radio" value="x" v-model="orderBy4">
                        <label for="orderByY4" class="pl-3">Y</label>
                        <input id="orderByY4" type="radio" value="y" v-model="orderBy4">
                        <label for="orderByCreationDate4" class="pl-3">Creation date</label>
                        <input id="orderByCreationDate4" type="radio" value="creationDate" v-model="orderBy4">     
                        <label for="orderByNumberOfParticipantsCount4" class="pl-3">Number Of Participants</label>
                        <input id="orderBynNumberOfParticipantsCount4" type="radio" value="numberOfParticipants" v-model="orderBy4">
                        <label for="orderByAlbumsCount4" class="pl-3">Albums Count</label>
                        <input id="orderByAlbumsCount4" type="radio" value="albumsCount" v-model="orderBy4">
                        <label for="orderByGenre4" class="pl-3">Genre</label>
                        <input id="orderByGenre4" type="radio" value="genre" v-model="orderBy4">
                        <label for="orderByLabelName4" class="pl-3">Label Name</label>
                        <input id="orderByLabelName4" type="radio" value="labelName" v-model="orderBy4">
                        <label for="orderByNone4" class="pl-3">None</label>
                        <input id="orderByNone4" type="radio" value="" v-model="orderBy4" checked>
                        
                        <label for="orderDirectionAsc4" class="ml-5">Asc</label>
                        <input id="orderDirectionAsc4" type="radio" value="a" v-model="orderDirection4">
                        <label for="orderDirectionDesc4" class="pl-3">Desc</label>
                        <input id="orderDirectionDesc4" type="radio" value="d" v-model="orderDirection4">
                        <label for="orderDirectionNone4" class="pl-3">None</label>
                        <input id="orderDirectionNone4" type="radio" value="" v-model="orderDirection4">
                    </div>
                    
                    <h3>Order by 5</h3>
                    <div>
                        <label for="orderById5">Id</label>
                        <input id="orderById5" type="radio" value="id" v-model="orderBy5">
                        <label for="orderByName5" class="pl-3">Name</label>
                        <input id="orderByName5" type="radio" value="name" v-model="orderBy5">
                        <label for="orderByX5" class="pl-3">X</label>
                        <input id="orderByX5" type="radio" value="x" v-model="orderBy5">
                        <label for="orderByY5" class="pl-3">Y</label>
                        <input id="orderByY5" type="radio" value="y" v-model="orderBy5">
                        <label for="orderByCreationDate5" class="pl-3">Creation date</label>
                        <input id="orderByCreationDate5" type="radio" value="creationDate" v-model="orderBy5">     
                        <label for="orderByNumberOfParticipantsCount5" class="pl-3">Number Of Participants</label>
                        <input id="orderBynNumberOfParticipantsCount5" type="radio" value="numberOfParticipants" v-model="orderBy5">
                        <label for="orderByAlbumsCount5" class="pl-3">Albums Count</label>
                        <input id="orderByAlbumsCount5" type="radio" value="albumsCount" v-model="orderBy5">
                        <label for="orderByGenre5" class="pl-3">Genre</label>
                        <input id="orderByGenre5" type="radio" value="genre" v-model="orderBy5">
                        <label for="orderByLabelName5" class="pl-3">Label Name</label>
                        <input id="orderByLabelName5" type="radio" value="labelName" v-model="orderBy5">
                        <label for="orderByNone5" class="pl-3">None</label>
                        <input id="orderByNone5" type="radio" value="" v-model="orderBy5" checked>
                        
                        <label for="orderDirectionAsc5" class="ml-5">Asc</label>
                        <input id="orderDirectionAsc5" type="radio" value="a" v-model="orderDirection5">
                        <label for="orderDirectionDesc5" class="pl-3">Desc</label>
                        <input id="orderDirectionDesc5" type="radio" value="d" v-model="orderDirection5">
                        <label for="orderDirectionNone5" class="pl-3">None</label>
                        <input id="orderDirectionNone5" type="radio" value="" v-model="orderDirection5">
                    </div>
                    
                    <h3>Order by 6</h3>
                    <div>
                        <label for="orderById6">Id</label>
                        <input id="orderById6" type="radio" value="id" v-model="orderBy6">
                        <label for="orderByName6" class="pl-3">Name</label>
                        <input id="orderByName6" type="radio" value="name" v-model="orderBy6">
                        <label for="orderByX6" class="pl-3">X</label>
                        <input id="orderByX6" type="radio" value="x" v-model="orderBy6">
                        <label for="orderByY6" class="pl-3">Y</label>
                        <input id="orderByY6" type="radio" value="y" v-model="orderBy6">
                        <label for="orderByCreationDate6" class="pl-3">Creation date</label>
                        <input id="orderByCreationDate6" type="radio" value="creationDate" v-model="orderBy6">     
                        <label for="orderByNumberOfParticipantsCount6" class="pl-3">Number Of Participants</label>
                        <input id="orderBynNumberOfParticipantsCount6" type="radio" value="numberOfParticipants" v-model="orderBy6">
                        <label for="orderByAlbumsCount6" class="pl-3">Albums Count</label>
                        <input id="orderByAlbumsCount6" type="radio" value="albumsCount" v-model="orderBy6">
                        <label for="orderByGenre6" class="pl-3">Genre</label>
                        <input id="orderByGenre6" type="radio" value="genre" v-model="orderBy6">
                        <label for="orderByLabelName6" class="pl-3">Label Name</label>
                        <input id="orderByLabelName6" type="radio" value="labelName" v-model="orderBy6">
                        <label for="orderByNone6" class="pl-3">None</label>
                        <input id="orderByNone6" type="radio" value="" v-model="orderBy6" checked>
                        
                        <label for="orderDirectionAsc6" class="ml-5">Asc</label>
                        <input id="orderDirectionAsc6" type="radio" value="a" v-model="orderDirection6">
                        <label for="orderDirectionDesc6" class="pl-3">Desc</label>
                        <input id="orderDirectionDesc6" type="radio" value="d" v-model="orderDirection6">
                        <label for="orderDirectionNone6" class="pl-3">None</label>
                        <input id="orderDirectionNone6" type="radio" value="" v-model="orderDirection6">
                    </div>
                    
                    <h3>Order by 7</h3>
                    <div>
                        <label for="orderById7">Id</label>
                        <input id="orderById7" type="radio" value="id" v-model="orderBy7">
                        <label for="orderByName7" class="pl-3">Name</label>
                        <input id="orderByName7" type="radio" value="name" v-model="orderBy7">
                        <label for="orderByX7" class="pl-3">X</label>
                        <input id="orderByX7" type="radio" value="x" v-model="orderBy7">
                        <label for="orderByY7" class="pl-3">Y</label>
                        <input id="orderByY7" type="radio" value="y" v-model="orderBy7">
                        <label for="orderByCreationDate7" class="pl-3">Creation date</label>
                        <input id="orderByCreationDate7" type="radio" value="creationDate" v-model="orderBy7">     
                        <label for="orderByNumberOfParticipantsCount7" class="pl-3">Number Of Participants</label>
                        <input id="orderBynNumberOfParticipantsCount7" type="radio" value="numberOfParticipants" v-model="orderBy7">
                        <label for="orderByAlbumsCount7" class="pl-3">Albums Count</label>
                        <input id="orderByAlbumsCount7" type="radio" value="albumsCount" v-model="orderBy7">
                        <label for="orderByGenre7" class="pl-3">Genre</label>
                        <input id="orderByGenre7" type="radio" value="genre" v-model="orderBy7">
                        <label for="orderByLabelName7" class="pl-3">Label Name</label>
                        <input id="orderByLabelName7" type="radio" value="labelName" v-model="orderBy7">
                        <label for="orderByNone7" class="pl-3">None</label>
                        <input id="orderByNone7" type="radio" value="" v-model="orderBy7" checked>
                        
                        <label for="orderDirectionAsc7" class="ml-5">Asc</label>
                        <input id="orderDirectionAsc7" type="radio" value="a" v-model="orderDirection7">
                        <label for="orderDirectionDesc7" class="pl-3">Desc</label>
                        <input id="orderDirectionDesc7" type="radio" value="d" v-model="orderDirection7">
                        <label for="orderDirectionNone7" class="pl-3">None</label>
                        <input id="orderDirectionNone7" type="radio" value="" v-model="orderDirection7">
                    </div>
                    
                    <h3>Order by 8</h3>
                    <div>
                        <label for="orderById8">Id</label>
                        <input id="orderById8" type="radio" value="id" v-model="orderBy8">
                        <label for="orderByName8" class="pl-3">Name</label>
                        <input id="orderByName8" type="radio" value="name" v-model="orderBy8">
                        <label for="orderByX8" class="pl-3">X</label>
                        <input id="orderByX8" type="radio" value="x" v-model="orderBy8">
                        <label for="orderByY8" class="pl-3">Y</label>
                        <input id="orderByY8" type="radio" value="y" v-model="orderBy8">
                        <label for="orderByCreationDate8" class="pl-3">Creation date</label>
                        <input id="orderByCreationDate8" type="radio" value="creationDate" v-model="orderBy8">     
                        <label for="orderByNumberOfParticipantsCount8" class="pl-3">Number Of Participants</label>
                        <input id="orderBynNumberOfParticipantsCount8" type="radio" value="numberOfParticipants" v-model="orderBy8">
                        <label for="orderByAlbumsCount8" class="pl-3">Albums Count</label>
                        <input id="orderByAlbumsCount8" type="radio" value="albumsCount" v-model="orderBy8">
                        <label for="orderByGenre8" class="pl-3">Genre</label>
                        <input id="orderByGenre8" type="radio" value="genre" v-model="orderBy8">
                        <label for="orderByLabelName8" class="pl-3">Label Name</label>
                        <input id="orderByLabelName8" type="radio" value="labelName" v-model="orderBy8">
                        <label for="orderByNone8" class="pl-3">None</label>
                        <input id="orderByNone8" type="radio" value="" v-model="orderBy8" checked>
                        
                        <label for="orderDirectionAsc8" class="ml-5">Asc</label>
                        <input id="orderDirectionAsc8" type="radio" value="a" v-model="orderDirection8">
                        <label for="orderDirectionDesc8" class="pl-3">Desc</label>
                        <input id="orderDirectionDesc8" type="radio" value="d" v-model="orderDirection8">
                        <label for="orderDirectionNone8" class="pl-3">None</label>
                        <input id="orderDirectionNone8" type="radio" value="" v-model="orderDirection8">
                    </div>
                    
                    <h3>Order by 9</h3>
                    <div>
                        <label for="orderById9">Id</label>
                        <input id="orderById9" type="radio" value="id" v-model="orderBy9">
                        <label for="orderByName9" class="pl-3">Name</label>
                        <input id="orderByName9" type="radio" value="name" v-model="orderBy9">
                        <label for="orderByX9" class="pl-3">X</label>
                        <input id="orderByX9" type="radio" value="x" v-model="orderBy9">
                        <label for="orderByY9" class="pl-3">Y</label>
                        <input id="orderByY9" type="radio" value="y" v-model="orderBy9">
                        <label for="orderByCreationDate9" class="pl-3">Creation date</label>
                        <input id="orderByCreationDate9" type="radio" value="creationDate" v-model="orderBy9">     
                        <label for="orderByNumberOfParticipantsCount9" class="pl-3">Number Of Participants</label>
                        <input id="orderBynNumberOfParticipantsCount9" type="radio" value="numberOfParticipants" v-model="orderBy9">
                        <label for="orderByAlbumsCount9" class="pl-3">Albums Count</label>
                        <input id="orderByAlbumsCount9" type="radio" value="albumsCount" v-model="orderBy9">
                        <label for="orderByGenre9" class="pl-3">Genre</label>
                        <input id="orderByGenre9" type="radio" value="genre" v-model="orderBy9">
                        <label for="orderByLabelName9" class="pl-3">Label Name</label>
                        <input id="orderByLabelName9" type="radio" value="labelName" v-model="orderBy9">
                        <label for="orderByNone9" class="pl-3">None</label>
                        <input id="orderByNone9" type="radio" value="" v-model="orderBy9" checked>
                        
                        <label for="orderDirectionAsc9" class="ml-5">Asc</label>
                        <input id="orderDirectionAsc9" type="radio" value="a" v-model="orderDirection9">
                        <label for="orderDirectionDesc9" class="pl-3">Desc</label>
                        <input id="orderDirectionDesc9" type="radio" value="d" v-model="orderDirection9">
                        <label for="orderDirectionNone9" class="pl-3">None</label>
                        <input id="orderDirectionNone9" type="radio" value="" v-model="orderDirection9">
                    </div>
                    
                    <h3>Filter</h3>
                    <div>
                        <label for="filterById">Id</label>
                        <input id="filterById" type="text" maxlength="8" v-model="filterById">
                    </div>
                    <div>
                        <label for="filterByName">Name</label>
                        <input id="filterByName" type="text" maxlength="256" v-model="filterByName">
                    </div>
                    <div>
                        <label for="filterByX">X</label>
                        <input id="filterByX" type="text" maxlength="8" v-model="filterByX">
                        
                        <label for="filterByXActionLess" class="pl-5"><</label>
                        <input id="filterByXActionLess" type="radio" value="<" v-model="filterByXAction" name="filterByXAction">
                        <label for="filterByXActionEqual" class="pl-5">==</label>
                        <input id="filterByXActionEqual" type="radio" value="==" v-model="filterByXAction" name="filterByXAction">
                        <label for="filterByXActionGreater" class="pl-5">\></label>
                        <input id="filterByXActionGreater" type="radio" value=">" v-model="filterByXAction" name="filterByXAction">
                        <label for="filterByXActionLessEqual" class="pl-5"><=</label>
                        <input id="filterByXActionLessEqual" type="radio" value="<=" v-model="filterByXAction" name="filterByXAction">     
                        <label for="filterByXActionGreaterEqual" class="pl-5">>=</label>
                        <input id="filterByXActionGreaterEqual" type="radio" value=">=" v-model="filterByXAction" name="filterByXAction">
                    </div>
                    <div>
                        <label for="filterByY">Y</label>
                        <input id="filterByY" type="text" maxlength="8" v-model="filterByY">
                        
                        <label for="filterByYActionLess" class="pl-5"><</label>
                        <input id="filterByYActionLess" type="radio" value="<" v-model="filterByYAction" name="filterByYAction">
                        <label for="filterByYActionEqual" class="pl-5">==</label>
                        <input id="filterByYActionEqual" type="radio" value="==" v-model="filterByYAction" name="filterByYAction">
                        <label for="filterByYActionGreater" class="pl-5">\></label>
                        <input id="filterByYActionGreater" type="radio" value=">" v-model="filterByYAction" name="filterByYAction">
                        <label for="filterByYActionLessEqual" class="pl-5"><=</label>
                        <input id="filterByYActionLessEqual" type="radio" value="<=" v-model="filterByYAction" name="filterByYAction">     
                        <label for="filterByYActionGreaterEqual" class="pl-5">>=</label>
                        <input id="filterByYActionGreaterEqual" type="radio" value=">=" v-model="filterByYAction" name="filterByYAction">
                    </div>
                    <div>
                        <label for="filterByCreationDate">Creation date</label>
                        <input id="filterByCreationDate" type="text" maxlength="10" v-model="filterByCreationDate">
                        
                        <label for="filterByCreationDateActionLess" class="pl-5"><</label>
                        <input id="filterByCreationDateActionLess" type="radio" value="<" v-model="filterByCreationDateAction" name="filterByCreationDateAction">
                        <label for="filterByCreationDateActionEqual" class="pl-5">==</label>
                        <input id="filterByCreationDateActionEqual" type="radio" value="==" v-model="filterByCreationDateAction" name="filterByCreationDateAction">
                        <label for="filterByCreationDateActionGreater" class="pl-5">\></label>
                        <input id="filterByCreationDateActionGreater" type="radio" value=">" v-model="filterByCreationDateAction" name="filterByCreationDateAction">
                        <label for="filterByCreationDateActionLessEqual" class="pl-5"><=</label>
                        <input id="filterByCreationDateActionLessEqual" type="radio" value="<=" v-model="filterByCreationDateAction" name="filterByCreationDateAction">     
                        <label for="filterByCreationDateActionGreaterEqual" class="pl-5">>=</label>
                        <input id="filterByCreationDateActionGreaterEqual" type="radio" value=">=" v-model="filterByCreationDateAction" name="filterByCreationDateAction">
                    </div>
                    <div>
                        <label for="filterByNumberOfParticipants">Number Of Participants</label>
                        <input id="filterByNumberOfParticipants" type="text" maxlength="8" v-model="filterByNumberOfParticipants">
                        
                        <label for="filterByNumberOfParticipantsActionLess" class="pl-5"><</label>
                        <input id="filterByNumberOfParticipantsActionLess" type="radio" value="<" v-model="filterByNumberOfParticipantsAction" name="filterByNumberOfParticipantsAction">
                        <label for="filterByNumberOfParticipantsActionEqual" class="pl-5">==</label>
                        <input id="filterByNumberOfParticipantsActionEqual" type="radio" value="==" v-model="filterByNumberOfParticipantsAction" name="filterByNumberOfParticipantsAction">
                        <label for="filterByNumberOfParticipantsActionGreater" class="pl-5">\></label>
                        <input id="filterByNumberOfParticipantsActionGreater" type="radio" value=">" v-model="filterByNumberOfParticipantsAction" name="filterByNumberOfParticipantsAction">
                        <label for="filterByNumberOfParticipantsActionLessEqual" class="pl-5"><=</label>
                        <input id="filterByNumberOfParticipantsActionLessEqual" type="radio" value="<=" v-model="filterByNumberOfParticipantsAction" name="filterByNumberOfParticipantsAction">     
                        <label for="filterByNumberOfParticipantsActionGreaterEqual" class="pl-5">>=</label>
                        <input id="filterByNumberOfParticipantsActionGreaterEqual" type="radio" value=">=" v-model="filterByNumberOfParticipantsAction" name="filterByNumberOfParticipantsAction">
                    </div>
                    <div>
                        <label for="filterByAlbumsCount">Albums Count</label>
                        <input id="filterByAlbumsCount" type="text" maxlength="8" v-model="filterByAlbumsCount">
                        
                        <label for="filterByAlbumsCountActionLess" class="pl-5"><</label>
                        <input id="filterByAlbumsCountActionLess" type="radio" value="<" v-model="filterByAlbumsCountAction" name="filterByAlbumsCountAction">
                        <label for="filterByAlbumsCountActionEqual" class="pl-5">==</label>
                        <input id="filterByAlbumsCountActionEqual" type="radio" value="==" v-model="filterByAlbumsCountAction" name="filterByAlbumsCountAction">
                        <label for="filterByAlbumsCountActionGreater" class="pl-5">\></label>
                        <input id="filterByAlbumsCountActionGreater" type="radio" value=">" v-model="filterByAlbumsCountAction" name="filterByAlbumsCountAction">
                        <label for="filterByAlbumsCountActionLessEqual" class="pl-5"><=</label>
                        <input id="filterByAlbumsCountActionLessEqual" type="radio" value="<=" v-model="filterByAlbumsCountAction" name="filterByAlbumsCountAction">     
                        <label for="filterByAlbumsCountActionGreaterEqual" class="pl-5">>=</label>
                        <input id="filterByAlbumsCountActionGreaterEqual" type="radio" value=">=" v-model="filterByAlbumsCountAction" name="filterByAlbumsCountAction">
                    </div>
                    <div>
                        <label for="filterByGenre">Genre</label>
                        
                        <label for="filterByGenreROCK" class="pl-5">ROCK</label>
                        <input id="filterByGenreROCK" type="radio" value="ROCK" v-model="filterByGenre" name="filterByGenre">
                        <label for="filterByGenreSOUL" class="pl-5">SOUL</label>
                        <input id="filterByGenreSOUL" type="radio" value="SOUL" v-model="filterByGenre" name="filterByGenre">
                        <label for="filterByGenreBLUES" class="pl-5">BLUES</label>
                        <input id="filterByGenreBLUES" type="radio" value="BLUES" v-model="filterByGenre" name="filterByGenre">
                        <label for="filterByGenrePOP" class="pl-5">POP</label>
                        <input id="filterByGenrePOP" type="radio" value="POP" v-model="filterByGenre" name="filterByGenre">
                        <label for="filterByGenrePOST_PUNK" class="pl-5">POST_PUNK</label>
                        <input id="filterByGenrePOST_PUNK" type="radio" value="POST_PUNK" v-model="filterByGenre" name="filterByGenre">
                        <label for="filterByGenreNONE" class="pl-5">None</label>
                        <input id="filterByGenreNONE" type="radio" value="" v-model="filterByGenre" name="filterByGenre">
                    </div>
                    <div>
                        <label for="filterByLabelName">Label Name</label>
                        <input id="filterByLabelName" type="text" maxlength="8" v-model="filterByLabelName">
                    </div>
                    
                    <button class="btn btn-info" v-on:click="filter()" type="submit">Filter</button>
                </div>
            `,

        data: function () {
            return {
                elementsCount: '',
                pageNumber: '',
                orderBy1: '',
                orderDirection1: '',
                orderBy2: '',
                orderDirection2: '',
                orderBy3: '',
                orderDirection3: '',
                orderBy4: '',
                orderDirection4: '',
                orderBy5: '',
                orderDirection5: '',
                orderBy6: '',
                orderDirection6: '',
                orderBy7: '',
                orderDirection7: '',
                orderBy8: '',
                orderDirection8: '',
                orderBy9: '',
                orderDirection9: '',
                filterById: '',
                filterByName: '',
                filterByX: '',
                filterByXAction: '',
                filterByY: '',
                filterByYAction: '',
                filterByCreationDate: '',
                filterByCreationDateAction: '',
                filterByNumberOfParticipants: '',
                filterByNumberOfParticipantsAction: '',
                filterByAlbumsCount: '',
                filterByAlbumsCountAction: '',
                filterByGenre: '',
                filterByLabelName: ''
            }
        },
        methods: {
            filter: function () {
                this.$emit('filter', {
                    'elementsCount': this.elementsCount,
                    'pageNumber': this.pageNumber,
                    'orderBy1': this.orderBy1,
                    'orderDirection1': this.orderDirection1,
                    'orderBy2': this.orderBy2,
                    'orderDirection2': this.orderDirection2,
                    'orderBy3': this.orderBy3,
                    'orderDirection3': this.orderDirection3,
                    'orderBy4': this.orderBy4,
                    'orderDirection4': this.orderDirection4,
                    'orderBy5': this.orderBy5,
                    'orderDirection5': this.orderDirection5,
                    'orderBy6': this.orderBy6,
                    'orderDirection6': this.orderDirection6,
                    'orderBy7': this.orderBy7,
                    'orderDirection7': this.orderDirection7,
                    'orderBy8': this.orderBy8,
                    'orderDirection8': this.orderDirection8,
                    'orderBy9': this.orderBy9,
                    'orderDirection9': this.orderDirection9,
                    'filterById': this.filterById,
                    'filterByName': this.filterByName,
                    'filterByX': this.filterByX,
                    'filterByXAction': this.filterByXAction,
                    'filterByY': this.filterByY,
                    'filterByYAction': this.filterByYAction,
                    'filterByCreationDate': this.filterByCreationDate,
                    'filterByCreationDateAction': this.filterByCreationDateAction,
                    'filterByNumberOfParticipants': this.filterByNumberOfParticipants,
                    'filterByNumberOfParticipantsAction': this.filterByNumberOfParticipantsAction,
                    'filterByAlbumsCount': this.filterByAlbumsCount,
                    'filterByAlbumsCountAction': this.filterByAlbumsCountAction,
                    'filterByGenre': this.filterByGenre,
                    'filterByLabelName': this.filterByLabelName,
                });
            }
        }
    }
);

Vue.component(
    'add-band',
    {
        template:
            `
            <div>
                <h1 class="col-xs-1 text-center">Add/update band</h1>
                <div>
                    <label for="id">Id</label>
                    <input id="id" type="number" maxlength="8" v-model="id">
                </div>
                <div>
                    <label for="name">Name</label>
                    <input id="name" type="text" maxlength="256" v-model="name">
                </div>
                <div>
                    <label for="x">X</label>
                    <input id="x" type="number" maxlength="8" v-model="x">
                </div>
                <div>
                    <label for="y">Y</label>
                    <input id="y" type="number" maxlength="8" v-model="y">
                </div>
                <div>
                    <label for="creationDate">Creation date</label>
                    <input id="creationDate" type="text" maxlength="10" v-model="creationDate">
                </div>
                <div>
                    <label for="numberOfParticipants">Number Of Participants</label>
                    <input id="numberOfParticipants" type="number" maxlength="8" v-model="numberOfParticipants">
                </div>
                <div>
                    <label for="albumsCount"/>Albums Count</label>
                    <input id="albumsCount" type="number" maxlength="8" v-model="albumsCount">
                </div>
                <div>
                    <label for="genre">Genre</label>
                    
                    <label for="filterByRatingROCK" class="pl-5">ROCK</label>
                    <input id="filterByRatingROCK" type="radio" value="ROCK" v-model="genre" name="genre">
                    <label for="filterByRatingSOUL" class="pl-5">SOUL</label>
                    <input id="filterByRatingSOUL" type="radio" value="SOUL" v-model="genre" name="genre">
                    <label for="filterByRatingBLUES" class="pl-5">BLUES</label>
                    <input id="filterByRatingBLUES" type="radio" value="BLUES" v-model="genre" name="genre">
                    <label for="filterByRatingPOP" class="pl-5">POP</label>
                    <input id="filterByRatingPOP" type="radio" value="POP" v-model="genre" name="mpaaRating">
                    <label for="filterByRatingPOST_PUNK" class="pl-5">POST_PUNK</label>
                    <input id="filterByRatingPOST_PUNK" type="radio" value="POST_PUNK" v-model="genre" name="mpaaRating">
                </div>
                <div>
                    <label for="labelName">Label Name</label>
                    <input id="labelName" type="text" maxlength="256" v-model="labelName">
                </div>
                
                <button v-if="id" class="btn btn-info" v-on:click="addBand()" type="submit">Update</button>
                <button v-else class="btn btn-info" v-on:click="addBand()" type="submit">Add</button>
            </div>
            `,

        data: function () {
            return {
                id: null,
                name: '',
                x: null,
                y: null,
                numberOfParticipants: null,
                albumsCount: null,
                genre: '',
                labelName: '',
            }
        },
        methods: {
            addBand: function () {
                let band = {
                    'name': this.name,
                    'x': this.x,
                    'y': this.y,
                    'creationDate': this.creationDate,
                    'numberOfParticipants': this.numberOfParticipants,
                    'albumsCount': this.albumsCount
                }
                if (this.genre) {
                    band.genre = this.genre
                }
                if (this.labelName) {
                    band.labelName = this.labelName
                }
                if (this.id) {
                    band.id = this.id
                }

                this.$emit('addband', band);
            },
            updateFields: function (band) {
                this.id = band.id
                this.name = band.name
                this.x = band.x
                this.y = band.y
                this.creationDate = moment(band.creationDate).format('YYYY-MM-DD')
                this.numberOfParticipants = band.numberOfParticipants
                this.albumsCount = band.albumsCount
                this.genre = band.genre
                this.labelName = band.labelName
            }
        }
    }
);

Vue.component(
    'additional-actions',
    {
        template:
            `
            <div>
                <h1 class="col-xs-1 text-center">Count by number of participants</h1>
                <table class="table table-striped table-bordered table-hover p-5 text-center">
                <tr>
                            <th>Number Of Participants</th>
                            <th>Count</th>
                        </tr>
                        <tr v-for="row in groupby">
                            <td>{{ row.numberOfParticipants }}</td>
                            <td>{{ row.count }}</td>
                        </tr>
                        </table> 
                <button class="btn btn-info" v-on:click="updateAvrg()" type="submit">Update groupby</button>
            </div>
            `,

        props: ["groupby"],
        methods: {
            updateAvrg: function () {
                this.$emit('calcavrg');
            }
        }
    }
);